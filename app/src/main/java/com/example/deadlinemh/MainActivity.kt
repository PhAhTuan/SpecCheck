package com.example.deadlinemh

import HomeScreenApp2
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.deadlinemh.data.Product
import com.example.deadlinemh.interfaceLogin.HomeScreenn
import com.example.deadlinemh.interfaceLogin.HomeScreennn
import com.example.deadlinemh.interfaceApp.HomeScreenApp1
import com.example.deadlinemh.interfaceApp.getFakeProductGroups
import com.example.deadlinemh.menu.testMenu
import com.example.deadlinemh.homengoai.HomeHome
import com.example.deadlinemh.interfaecSS.HomeScreenSS
import com.example.deadlinemh.ui.theme.DeadlineMHTheme
import kotlin.collections.find

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DeadlineMHTheme {
                val navController = rememberNavController()
                Surface(modifier = Modifier.fillMaxSize())
                {
                    val allFakeProducts: List<Product> = getFakeProductGroups(navController).flatMap { it.products }
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
                        //---------------
                        composable(
                            route = "trangchu/{leftProductId}",
                            arguments = listOf(navArgument("leftProductId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val leftProductId = backStackEntry.arguments?.getInt("leftProductId")
                            HomeScreenApp1(navController = navController, leftProductId = leftProductId)
                        }
                        composable("dangkyyy"){
                            HomeScreennn(navController)
                        }
                        composable("menucon"){
                            testMenu(navController, onClose = {navController.popBackStack()})
                        }
                        composable("chitietsanpham/{productId}") { backStackEntry ->
                            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
                            val product = allFakeProducts.find { it.id == productId }

                            if (product != null) {
                                HomeScreenApp2(navController = navController,productId = product.id )
                            } else {
                                Text("Không tìm thấy sản phẩm")
                            }
                        }
                        //-------------------------------
                        composable(
                            route = "compare/{leftProductId}",
                            arguments = listOf(navArgument("leftProductId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val leftProductId = backStackEntry.arguments?.getInt("leftProductId")
                            HomeScreenSS(navController = navController, leftProductId = leftProductId)
                        }
                        composable(
                            route = "compare/{leftProductId}/{rightProductId}",
                            arguments = listOf(
                                navArgument("leftProductId") { type = NavType.IntType },
                                navArgument("rightProductId") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            val leftProductId = backStackEntry.arguments?.getInt("leftProductId")
                            val rightProductId = backStackEntry.arguments?.getInt("rightProductId")
                            HomeScreenSS(
                                navController = navController,
                                leftProductId = leftProductId,
                                rightProductId = rightProductId,)
                        }
                            //-------------------------
                            composable("xemtatca"){
                            testMenu(navController, onClose = {navController.popBackStack()})
                            }
                        composable("homethanhtaskbar") {
                            HomeScreenApp1(navController)
                        }
                        }
                    }
                }
            }
        }
    }



