package com.example.cyber2.ui.theme.screens.students

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.cyber2.R
import com.example.cyber2.data.StudentViewModel
import com.example.cyber2.navigation.ROUTE_VIEW_STUDENTS

@Composable
fun AddstudentScreen(navController: NavController) {
    val imageUri = rememberSaveable() { mutableStateOf<Uri?>(value = null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent())
    { uri: Uri? -> uri?.let { imageUri.value = it } }
    var name by remember { mutableStateOf(value = "") }
    var gender by remember { mutableStateOf(value = "") }
    var nationality by remember { mutableStateOf(value = "") }
    var course by remember { mutableStateOf(value = "") }
    var summary by remember { mutableStateOf(value = "") }
    val studentViewModel: StudentViewModel = viewModel()
    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(Color.Green)
                .padding(20.dp)
        )
        {
            Text(
                text = "ADD NEW STUDENT",
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Card(shape = CircleShape, modifier = Modifier.padding(20.dp).size(200.dp)) {
            AsyncImage(
                model = imageUri.value ?: R.drawable.ic_person,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp).clickable { launcher.launch("image/*") })
        }
        Text(text = "Upload picture here")

        OutlinedTextField(
            value = name,
            onValueChange = { newName -> name = newName },
            label = { Text(text = "Enter student name") },
            placeholder = { Text(text = "Please enter student name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = gender,
            onValueChange = { newGender -> gender = newGender },
            label = { Text(text = "Enter student gender") },
            placeholder = { Text(text = "Please enter student gender") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = nationality,
            onValueChange = { newNationality -> nationality = newNationality },
            label = { Text(text = "Enter student nationality") },
            placeholder = { Text(text = "Please enter student nationality") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = course,
            onValueChange = { newCourse -> course = newCourse },
            label = { Text(text = "Enter student course") },
            placeholder = { Text(text = "Please enter student course") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = summary,
            onValueChange = { newSummary -> summary = newSummary },
            label = { Text(text = "Enter student summary") },
            placeholder = { Text(text = "Please enter student summary") },
            modifier = Modifier.fillMaxWidth().height(100.dp), singleLine = false
        )
        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    imageUri.value?.let {
                        studentViewModel.uploadStudentWithImage(
                            it,
                            context,
                            name,
                            gender,
                            course,
                            summary,
                            nationality,
                            navController
                        )
                    } ?: Toast.makeText(context, "Please pick an image", Toast.LENGTH_LONG).show()
                },
                modifier = Modifier.wrapContentWidth().align(Alignment.CenterVertically),
                colors = ButtonDefaults.buttonColors(Color.Blue)
            )
            { Text(text = "SAVE") }

            Spacer(modifier = Modifier.width(150.dp))

            Button(
                onClick = {}, modifier = Modifier.wrapContentWidth()
                    .align(Alignment.CenterVertically),
                colors = ButtonDefaults.buttonColors(Color.Blue)
            )
            {
                Text(
                    text = "GO BACK ",
                    modifier = Modifier.clickable { navController.navigate(ROUTE_VIEW_STUDENTS) })
            }

        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddstudentScreenPreview(){
    AddstudentScreen(rememberNavController())
}