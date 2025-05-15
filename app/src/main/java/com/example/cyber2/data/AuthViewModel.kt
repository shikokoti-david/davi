package com.example.cyber2.data

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.cyber2.models.UserModel
import com.example.cyber2.navigation.ROUTE_DASHBOARD
import com.example.cyber2.navigation.ROUTE_LOGIN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow

class AuthViewModel: ViewModel() {
    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private val _isloading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)

    fun signup(firstname : String,lastname : String,email_address:
    String,password: String,
               navController: NavController,
               context: Context
    ){
        if (firstname.isBlank() || lastname.isBlank() ||
            email_address.isBlank() || password.isBlank()){

            Toast.makeText(context,"Please fill all the fields", Toast.LENGTH_LONG).show()
            return
        }
        _isloading.value = true

        mAuth.createUserWithEmailAndPassword(email_address,password)
            .addOnCompleteListener { task ->
                _isloading.value = false
                if (task.isSuccessful){
                    val userId = mAuth.currentUser?.uid ?:""
                    val userData = UserModel(firstname = firstname,lastname = lastname,
                        email_address = email_address,password = password,userId = userId)
                    saveUserToDataBase(userId,userData,navController,context)
                }
                else{
                    _errorMessage.value = task.exception?.message
                    Toast.makeText(context,"User successfully registered",Toast.LENGTH_LONG).show()
                }
            }

    }
    fun saveUserToDataBase(userId: String,userData: UserModel,navController:
    NavController,context: Context){
        val regRef = FirebaseDatabase.getInstance()
            .getReference("Users/$userId")
        regRef.setValue(userData).addOnCompleteListener { regRef ->
            if (regRef.isSuccessful){
                Toast.makeText(context,"User successfully registered", Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_LOGIN)
            }
            else{
                _errorMessage.value = regRef.exception?.message
                Toast.makeText(context,"Database Error", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun login(email: String,password: String,navController: NavController,context: Context){
        if (email.isBlank() || password.isBlank()){
            Toast.makeText(context,"Email and password required", Toast.LENGTH_LONG).show()
            return
        }
        _isloading.value = true

        mAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                _isloading.value = false
                if (task.isSuccessful){
                    Toast.makeText(context,"User successfully logged in", Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_DASHBOARD)
                }
                else{
                    _errorMessage.value = task.exception?.message
                    Toast.makeText(context,"Login sucsess",Toast.LENGTH_LONG).show()

                }
            }
    }
}