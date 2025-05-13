
package com.example.cyber2.ui.theme.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cyber2.R
import com.example.cyber2.data.AuthViewModel
import com.example.cyber2.navigation.ROUTE_REGISTER

@Composable
fun LogInScreen(navController: NavController) {
    val authViewModel : AuthViewModel = viewModel ()
    var email by remember { mutableStateOf(value = "") }
    var password by remember { mutableStateOf(value = "") }
    val context = LocalContext.current
    val passwordVisible by remember { mutableStateOf(false) }


    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center) {
        Text(text = "LOG IN HERE",
            fontSize = 30.sp,
            fontFamily = FontFamily.SansSerif,
            fontStyle = FontStyle.Normal,
            color = androidx.compose.ui.graphics.Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.
            background(androidx.compose.ui.graphics.Color.Blue)
                .fillMaxWidth().padding(20.dp))

        Spacer(modifier = Modifier.height(10.dp))

        Image(painter = painterResource(id = R.drawable.logo1),
            contentDescription = "logo",
            modifier = Modifier.wrapContentHeight()
                .fillMaxWidth().height(100.dp))

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(value = email, onValueChange
        = {newemail -> email = newemail},
            label = {Text(text = "Enter your email address")},
            placeholder = {Text(text = "Pleasa enter your email address")},
            modifier = Modifier.wrapContentWidth().
            align(Alignment.CenterHorizontally) )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(value = password, onValueChange =
            {newpassword -> password = newpassword},
            label = {Text(text = "Enter your password")},
            placeholder = {Text(text = "Please enter your password")},
            modifier = Modifier.wrapContentWidth().
            align(Alignment.CenterHorizontally),visualTransformation = if (passwordVisible)
                VisualTransformation.None else PasswordVisualTransformation())


        Spacer(modifier = Modifier.height(10.dp) )

        Button(onClick = {
            authViewModel.login(email,password,navController,context)
        }, modifier = Modifier.wrapContentWidth().
        align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
            androidx.compose.ui.graphics.Color.Green))
        { Text(text = "Log In") }

        Spacer(modifier = Modifier.height(10.dp) )

        Text(text = buildAnnotatedString { append("If not registered,click here")},
            modifier = Modifier.wrapContentWidth().
            align(Alignment.CenterHorizontally).clickable {
                navController.navigate(ROUTE_REGISTER)

        })

    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LogInScreenPreview(){
    LogInScreen(rememberNavController())
}

