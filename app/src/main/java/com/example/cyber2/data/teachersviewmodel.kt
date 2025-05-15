package com.example.cyber2.data

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.cyber2.models.StudentModel
import com.example.cyber2.models.TeachersModel
import com.example.cyber2.navigation.ROUTE_VIEW_STUDENTS
import com.example.cyber2.network.ImgurSevice
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


class TeachersViewModel : ViewModel() {
    val teachersId = FirebaseAuth.getInstance().currentUser?.uid
    private val database = FirebaseDatabase.getInstance().reference.child("Teachers")
    private fun getImgurService(): ImgurSevice{
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.imgur.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ImgurSevice::class.java)
    }
    private fun getFileFromUri(context: Context, uri: Uri):
            File? {
        return try {
            val inputStream = context.contentResolver
                .openInputStream(uri)
            val file = File.createTempFile("temp_image", ".jpg", context.cacheDir)
            file.outputStream().use { output ->
                inputStream?.copyTo(output)
            }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun uploadTeacherWithImage(
        uri: Uri,
        context: Context,
        name: String,
        gender: String,
        course: String,
        summary: String,
        nationality : String,
        navController: NavController
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val file = getFileFromUri(context, uri)
                if (file == null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Failed to process image", Toast.LENGTH_SHORT)
                            .show()
                    }
                    return@launch
                }
                val reqFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("image", file.name, reqFile)
                val response = getImgurService().uploadImage(
                    body,
                    "Client-ID f998b6fd8f2ef75"
                )
                if (response.isSuccessful) {
                    val imageUrl = response.body()?.data?.link ?: ""
                    val teachersId = database.push().key ?: ""
                    val teachers = TeachersModel(
                        name, gender, course, summary,nationality, imageUrl, teachersId

                    )
                    database.child(teachersId).setValue(teachers)
                        .addOnSuccessListener {
                            viewModelScope.launch {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        context,
                                        "Teacher saved successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navController.navigate(ROUTE_VIEW_STUDENTS)
                                }
                            }
                        }.addOnFailureListener {
                            viewModelScope.launch {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        context,
                                        "Failed to save teacher",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Upload error", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Exception: ${e.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    fun viewTeachers(
        teacher: MutableState<TeachersModel>,
        teachers: SnapshotStateList<TeachersModel>,
        context: Context
    ): SnapshotStateList<TeachersModel> {
        val ref = FirebaseDatabase.getInstance().getReference("Teachers")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                teachers.clear()
                for (snap in snapshot.children) {
                    val value = snap.getValue(TeachersModel::class.java)
                    value?.let {
                        teachers.add(it)
                    }
                }
                if (teachers.isNotEmpty()) teacher.value = teachers.first()
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    context,
                    "Failed to fetch teachers: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        return teachers
    }
    fun updateTeacher(
        context: Context,
        navController: NavController,
        name: String,
        gender: String,
        course: String,
        summary: String,
        nationality: String,
        teachersId : String
    ) {
        val TeachersId = ""
        val databaseReference = FirebaseDatabase.getInstance()
            .getReference("Teachers/$TeachersId")
        val updatedTeacher = TeachersModel(
            "",
            name, gender,
            course, summary, nationality,  teachersId
        )
        databaseReference.setValue(updatedTeacher)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Teachers Updated Successfully", Toast.LENGTH_LONG)
                        .show()
                    navController.navigate(ROUTE_VIEW_STUDENTS)
                } else {
                    Toast.makeText(context, "Teachers update failed", Toast.LENGTH_LONG).show()
                }

            }
    }
    fun deleteTeacher(
        context: Context, teachersId: String,
        navController: NavController
    ) {
        AlertDialog.Builder(context)
            .setTitle("Delete teacher")
            .setMessage("Are you sure you want to delete this teacher?")
            .setPositiveButton("Yes") { _, _ ->
                val databaseReference = FirebaseDatabase.getInstance()
                    .getReference("Teachers/$teachersId")
                databaseReference.removeValue().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            context,
                            "Teacher deleted Successfully",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(context, "Teacher not deleted", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }
}
