package com.example.deadlinemh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.deadlinemh.GiaoDienLogin.HomeScreenn
import com.example.deadlinemh.GiaoDienLogin.HomeScreennn
import com.example.deadlinemh.homengoai.HomeHome
import com.example.deadlinemh.interfaceApp.HomeScreenApp1
import com.example.deadlinemh.interfaceApp.HomeScreenApp2
import com.example.deadlinemh.menu.testMenu
import com.example.deadlinemh.ui.theme.DeadlineMHTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DeadlineMHTheme {
                val navController = rememberNavController()
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavHost(navController = navController, startDestination = "welcome") {
                        composable("welcome") {
                            HomeHome(navController)
                        }
                        composable("homeMain") {
                            HomeScreennn(navController)
                        }
                        composable("dangky") {
                            HomeScreenn(navController)
                        }
                        composable("dangnhap") {
                            HomeScreennn(navController)
                        }
                        composable("trangchu") {
                            HomeScreenApp1(navController)
                        }
                        composable("dangkyyy") {
                            HomeScreennn(navController)
                        }
                        composable("menucon") {
                            testMenu(navController, onClose = { navController.popBackStack() })
                        }
                        composable(
                            route = "chitietsanpham/{productId}",
                            arguments = listOf(navArgument("productId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val productId = backStackEntry.arguments?.getInt("productId") ?: 1
                            HomeScreenApp2(navController = navController, productId = productId)
                        }
                    }
                }
            }
        }
    }
}