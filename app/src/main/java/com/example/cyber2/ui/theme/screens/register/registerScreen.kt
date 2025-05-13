package com.example.cyber2.ui.theme.screens.register

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.cyber2.navigation.ROUTE_LOGIN
import com.example.cyber2.navigation.ROUTE_REGISTER

@Composable
fun RegisterScreen(navController: NavController){
    val authViewModel: AuthViewModel = viewModel ()
    var firstname by remember { mutableStateOf(value = "") }
    var lastname by remember { mutableStateOf(value = "") }
    var email_address by remember {mutableStateOf(value = "")}
    var password by remember {mutableStateOf(value = "")}
    val context = LocalContext.current
    val passwordVisible by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center ){
        Text(text = "Register Here",
            fontSize = 30.sp,
            fontFamily = FontFamily.SansSerif,
            fontStyle = FontStyle.Normal,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.background(Color.Blue)
                .fillMaxWidth().padding(20.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Image(painter = painterResource(id = R.drawable.logo3),
            contentDescription = "logo",
            modifier = Modifier.wrapContentHeight().fillMaxWidth()
                .height(100.dp))

        Spacer(modifier = Modifier.height(10.dp) )

        OutlinedTextField(value = firstname, onValueChange
        = {newFirstName -> firstname = newFirstName},
            label = { Text(text = "Enter first name")},
            placeholder = {Text(text = "Please enter first name")},
            modifier = Modifier.wrapContentWidth().
            align(Alignment.CenterHorizontally),leadingIcon = {
                Icon(imageVector = Icons.Default.Person,
                    contentDescription = "Person Icon")})

        Spacer(modifier = Modifier.height(10.dp) )

        OutlinedTextField(value = lastname, onValueChange
        = {newLastName -> lastname = newLastName},
            label = {Text(text = "Enter last name")},
            placeholder = {Text(text = "Please enter last name")},
            modifier = Modifier.wrapContentWidth()
                .align (Alignment.CenterHorizontally),
            leadingIcon = { Icon(imageVector = Icons.Default.Person,
                contentDescription = "Person Icon")})


        Spacer(modifier = Modifier.height(10.dp) )

        OutlinedTextField(value = email_address, onValueChange
        = {newemail_address -> email_address = newemail_address},
            label = {Text(text = "Enter your email address")},
            placeholder = {Text(text = "Pleasa enter your email address")},
            modifier = Modifier.wrapContentWidth().align(Alignment.CenterHorizontally),
            leadingIcon = { Icon(imageVector = Icons.Default.Email,
                contentDescription = "Email Icon") } )

        Spacer(modifier = Modifier.height(10.dp) )

        OutlinedTextField(value = password, onValueChange
        = {newpassword -> password = newpassword},
            label = {Text(text = "Enter your password")},
            placeholder = {Text(text = "Please enter your password")},
            modifier = Modifier.wrapContentWidth().
            align(Alignment.CenterHorizontally), leadingIcon =
                { Icon(imageVector = Icons.Default.Lock,
                    contentDescription = "Password Icon")},
            visualTransformation = if (passwordVisible)
                VisualTransformation.None else PasswordVisualTransformation())

        Spacer(modifier = Modifier.height(10.dp) )

        Button(onClick = {
            authViewModel.signup(firstname,lastname,email_address,password,navController,context)
                         },
            modifier = Modifier.wrapContentWidth().align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(Color.Green))
        { Text(text = "Register") }

        Spacer(modifier = Modifier.height(10.dp) )

        Text(text = buildAnnotatedString { append("If already registered,click here")},
            modifier = Modifier.wrapContentWidth().align(Alignment.CenterHorizontally).clickable{
                navController.navigate(ROUTE_LOGIN)

        })
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview(){
    RegisterScreen(rememberNavController())

}