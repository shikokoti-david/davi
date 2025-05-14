package com.example.cyber2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cyber2.ui.theme.screens.SplashScreen
import com.example.cyber2.ui.theme.screens.dashboard.DashBoardScreen
import com.example.cyber2.ui.theme.screens.login.LogInScreen
import com.example.cyber2.ui.theme.screens.register.RegisterScreen
import com.example.cyber2.ui.theme.screens.students.AddstudentScreen
import com.example.cyber2.ui.theme.screens.students.UpdatestudentScreen
import com.example.cyber2.ui.theme.screens.students.ViewStudents
import com.example.cyber2.ui.theme.screens.teachers.Addteachersscreen
import com.example.cyber2.ui.theme.screens.teachers.UpdateteachersScreen
import com.example.cyber2.ui.theme.screens.teachers.ViewTeachers

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController(),startDestination:String = ROUTE_SPLASH){
    NavHost (navController = navController,startDestination=startDestination){
        composable (ROUTE_SPLASH){ SplashScreen{navController.navigate(ROUTE_REGISTER)
        {popUpTo(ROUTE_SPLASH){inclusive=true} } } }
        composable (ROUTE_REGISTER){ RegisterScreen(navController) }
        composable ( ROUTE_LOGIN ){ LogInScreen(navController) }
        composable (ROUTE_DASHBOARD){ DashBoardScreen(navController) }
        composable (ROUTE_ADD_STUDENTS){ AddstudentScreen(navController) }
        composable(ROUTE_VIEW_STUDENTS){ ViewStudents(navController) }
        composable("$ROUTE_UPDATE_STUDENT/{studentId}") {passedData -> UpdatestudentScreen(
            navController, passedData.arguments?.getString("studentId")!! )}
        composable (ROUTE_ADD_TEACHERS){ Addteachersscreen(navController) }
        composable ( ROUTE_VIEW_TEACHERS ){ ViewTeachers(navController) }
        composable (ROUTE_UPDATE_TEACHERS){  }
        composable("$ROUTE_UPDATE_TEACHERS/{teachersId}") { passedData ->
            UpdateteachersScreen(
                navController, passedData.arguments?.getString("teachersId")!! )
        }
    }


}

