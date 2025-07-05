package com.example.deadlinemh

import HomeScreenApp2
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.deadlinemh.GiaoDienLogin.HomeScreenn
import com.example.deadlinemh.GiaoDienLogin.HomeScreennn
import com.example.deadlinemh.GiaodienApp.HomeScreenApp1
import com.example.deadlinemh.Menu.testMenu
import com.example.deadlinemh.homengoai.HomeHome
import com.example.deadlinemh.ui.theme.DeadlineMHTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DeadlineMHTheme {
                val navController = rememberNavController()
                Surface(modifier = Modifier.fillMaxSize())
                {
                    NavHost(navController = navController, startDestination ="welcome"){
                        composable("welcome"){
                            HomeHome(navController)
                        }
                        composable("homeMain"){
                            HomeScreennn(navController)
                        }
                        composable("dangky"){
                            HomeScreenn(navController)
                        }
                        composable("dangnhap"){
                            HomeScreennn(navController)
                        }
                        composable("trangchu"){
                            HomeScreenApp1(navController)
                        }
                        composable("dangkyyy"){
                            HomeScreennn(navController)
                        }
                        composable("menucon"){
                            testMenu(navController, onClose = {navController.popBackStack()})
                        }
                        composable("chitietsanpham"){
                            HomeScreenApp2(navController)
                        }
                    }
                }
            }
        }
    }
}

