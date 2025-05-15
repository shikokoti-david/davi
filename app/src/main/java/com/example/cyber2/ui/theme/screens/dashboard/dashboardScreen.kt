package com.example.cyber2.ui.theme.screens.dashboard

import android.R.attr.data
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cyber2.R
import com.example.cyber2.navigation.ROUTE_ADD_STUDENTS
import com.example.cyber2.navigation.ROUTE_ADD_TEACHERS

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardScreen(navController: NavController) {
    val selectedItem = remember { mutableStateOf(0)  }
    val context = LocalContext.current
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.Red)
            { NavigationBarItem(
                selected = selectedItem.value == 0,
                onClick = {selectedItem.value = 0
                            val sendIntent = Intent().apply{
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT,
                                    "download here:https/www.download.com")
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent,null)
                    context.startActivity(shareIntent)
                          },
                icon = { Icon(Icons.Filled.Share, contentDescription = "Share")},
                label = {Text(text = "Share")},
                alwaysShowLabel = true
            )
                NavigationBarItem(
                    selected = selectedItem.value == 1,
                    onClick = {selectedItem.value = 1 },
                    icon = { Icon(Icons.Filled.Phone, contentDescription = "Phone")},
                    label = {Text(text = "Phone")},
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                selected = selectedItem.value == 2,
                onClick = {selectedItem.value = 2
                          val intent = Intent(Intent.ACTION_SENDTO).apply {
                              ("mailto:elijawagaki@gmail.com")
                              putExtra(Intent.EXTRA_SUBJECT,"Inquiry")
                              putExtra(Intent.EXTRA_TEXT,"Hello, I would like to join you.Please help")
                          }
                          context.startActivity(intent)
                          },
                icon = { Icon(Icons.Filled.Email, contentDescription = "Email")},
                label = {Text(text = "Email")},
                alwaysShowLabel = true
            ) }
        }
    )
    { innerPadding ->


    Box {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.padding(innerPadding)
        )
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = { Text(text = "PRO SCHOOLS") },
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Home"
                    )
                }
            },
            actions = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Profile"
                    )
                }

                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search"
                    )
                }

                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Blue,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White,
                actionIconContentColor = Color.White
            )


        )
        Row(modifier = Modifier.wrapContentWidth()) {
            Card(
                modifier = Modifier.padding(10.dp).clickable{
                    navController.navigate(ROUTE_ADD_STUDENTS)
                },
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(10.dp),
                colors = CardDefaults.cardColors(Color.Blue)
            )
            {
                Box(
                    modifier = Modifier.height(80.dp).padding(25.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Students",
                        color = Color.White
                    )
                }
            }
        }
        Row {
            Card(
                modifier = Modifier.padding(10.dp).clickable{navController.navigate((ROUTE_ADD_TEACHERS))},
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(10.dp),
                colors = CardDefaults.cardColors(Color.Blue)
            )
            {
                Box(
                    modifier = Modifier.height(80.dp).padding(25.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Teachers",
                        color = Color.White
                    )
                }
            }
                }
                }
            }
        }




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashBoardScreenPreview(){
    DashBoardScreen(rememberNavController())
}