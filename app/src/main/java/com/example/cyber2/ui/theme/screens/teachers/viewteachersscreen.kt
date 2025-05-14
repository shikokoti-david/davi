package com.example.cyber2.ui.theme.screens.teachers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.cyber2.data.StudentViewModel
import com.example.cyber2.data.TeachersViewModel
import com.example.cyber2.models.TeachersModel
import com.example.cyber2.navigation.ROUTE_UPDATE_TEACHERS


@Composable
fun ViewTeachers(navController: NavHostController){
    val context = LocalContext.current
    val teachersRepository = TeachersViewModel()
    val emptyUploadState = remember { mutableStateOf(TeachersModel("","","","","","",""))}
    val emptyUploadListState = remember {mutableStateListOf<TeachersModel>()}
    val teachers = teachersRepository.viewTeachers(
        emptyUploadState,emptyUploadListState, context)

    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally){

        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "All Teachers",
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                color= Color.Black)

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn()
            {items (teachers){
                TeachersItem(
                    name = it.name,
                    gender = it.gender,
                    course = it.course,
                    summary = it.summary,
                    nationality = it.nationality,
                    teachersId = it.teachersId,
                    imageUrl = it.imageUrl,
                    navController = navController,
                    teachersRepository = teachersRepository,

                )}
            }
        }
    }
}
@Composable
fun TeachersItem(name:String,gender:String,course:String,
                summary: String,nationality : String,teachersId:String,imageUrl: String,navController: NavHostController,
                teachersRepository: TeachersViewModel){
    val context = LocalContext.current
    Column (modifier = Modifier.fillMaxWidth()){
        Card (modifier = Modifier
            .padding(10.dp)
            .height(210.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors
                (containerColor = Color.Gray))
        {
            Row {
                Column {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(200.dp)
                            .height(150.dp)
                            .padding(10.dp)
                    )

                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        Button(onClick = {
                            teachersRepository.deleteTeacher(context,teachersId,navController)
                        },shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(Color.Red)
                        ) {
                            Text(text = "REMOVE",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp)
                        }
                        Button(onClick = {
                            val teachersId = ""
                            navController.navigate("$ROUTE_UPDATE_TEACHERS/$teachersId")
                        },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(Color.Green)
                        ) {
                            Text(text = "UPDATE",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp)
                        }
                    }
                }
                Column (modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                    .verticalScroll(rememberScrollState())){
                    Text(text = "TEACHERS NAME",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold)

                    Text(text = name,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold)

                    Text(text = "TEACHERS  GENDER",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold)

                    Text(text = gender,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold)

                    Text(text = "TEACHERS  NATIONALITY",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold)

                    Text(text = nationality,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold)

                    Text(text = "TEACHERS TEACHING COURSE",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold)

                    Text(text = course,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold)

                    Text(text = "SUMMARY",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold)

                    Text(text = summary,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}


@Preview
@Composable
fun ViewTeachersPreview(){
    ViewTeachers(rememberNavController())
}