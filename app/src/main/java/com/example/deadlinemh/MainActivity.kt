package com.example.deadlinemh

import HomeScreenApp2
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
import com.example.deadlinemh.interfaceApp.AccountScreen
import com.example.deadlinemh.interfaceApp.HomeSRFavorite
import com.example.deadlinemh.interfaecSS.HomeScreenSS
import com.example.deadlinemh.ui.theme.DeadlineMHTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()
    val db = Firebase.firestore
    val isDarkMode = remember { mutableStateOf(false) }


    DisposableEffect(Unit) {
        val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                db.collection("users").document(user.uid)
                    .get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            isDarkMode.value = document.getBoolean("isDarkMode") ?: false
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            navController.context,
                            "Lỗi tải Dark Mode: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } else {
                isDarkMode.value = false
            }
        }
        auth.addAuthStateListener(authStateListener)
        onDispose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    DeadlineMHTheme(darkTheme = isDarkMode.value) {
        Surface(modifier = Modifier.fillMaxSize()) {
            val allFakeProducts: List<Product> = getFakeProductGroups(navController).flatMap { it.products }
            val startDestination = if (auth.currentUser != null) "trangchu" else "welcome"

            NavHost(navController = navController, startDestination = startDestination) {
                composable("welcome") {
                    DeadlineMHTheme(darkTheme = false) {
                        HomeHome(navController)
                    }
                }
                composable("homeMain") {
                    DeadlineMHTheme(darkTheme = false) {
                        HomeScreennn(navController)
                    }
                }
                composable("dangky") {
                    DeadlineMHTheme(darkTheme = false) {
                        HomeScreenn(navController)
                    }
                }
                composable("dangnhap") {
                    DeadlineMHTheme(darkTheme = false) {
                        HomeScreennn(navController)
                    }
                }
                composable("trangchu") {
                    HomeScreenApp1(navController)
                }
                composable(
                    route = "trangchu/{leftProductId}",
                    arguments = listOf(navArgument("leftProductId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val leftProductId = backStackEntry.arguments?.getInt("leftProductId")
                    HomeScreenApp1(
                        navController = navController,
                        leftProductId = leftProductId
                    )
                }
                composable("dangkyyy") {
                    DeadlineMHTheme(darkTheme = false) {
                        HomeScreennn(navController)
                    }
                }
                composable("menucon") {
                    testMenu(navController, onClose = { navController.popBackStack() })
                }
                composable(route = "detail/{productId}",
                    arguments = listOf(navArgument("productId") { type = NavType.IntType })) { backStackEntry ->
                    val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
                    val product = allFakeProducts.find { it.id == productId }
                    if (product != null) {
                        HomeScreenApp2(
                            navController = navController,
                            productId = product.id
                        )
                    } else {
                        Text("Không tìm thấy sản phẩm")
                    }
                }
                composable(
                    route = "compare/{leftProductId}",
                    arguments = listOf(navArgument("leftProductId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val leftProductId = backStackEntry.arguments?.getInt("leftProductId")
                    HomeScreenSS(
                        navController = navController,
                        leftProductId = leftProductId
                    )
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
                        rightProductId = rightProductId
                    )
                }
                composable("xemtatca") {
                    testMenu(navController, onClose = { navController.popBackStack() })
                }
                composable("homethanhtaskbar") {
                    HomeScreenApp1(navController)
                }
                composable("dangxuat") {
                    DeadlineMHTheme(darkTheme = false) {
                        HomeScreennn(navController)
                    }
                }
                composable("account") {
                    AccountScreen(
                        navController = navController,
                        isDarkMode = isDarkMode,
                        onDarkModeToggle = { newValue ->
                            Log.d("AccountScreen", "onDarkModeToggle called with newValue: $newValue, type: ${newValue.javaClass}")
                            isDarkMode.value = newValue
                            if (auth.currentUser != null) {
                                db.collection("users").document(auth.currentUser!!.uid)
                                    .update("isDarkMode", newValue)
                                    .addOnFailureListener { e ->
                                        Toast.makeText(
                                            navController.context,
                                            "Lỗi lưu Dark Mode: ${e.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }
                        }
                    )
                }
                composable("yeuthich") {
                    if (FirebaseAuth.getInstance().currentUser == null) {
                        navController.navigate("dangnhap") {
                            popUpTo(navController.graph.startDestinationId)
                        }
                    } else {
                        HomeSRFavorite(navController = navController)
                    }
                }
                composable("yeuthich"){
                    HomeSRFavorite(navController)
                }
            }
        }
    }
}