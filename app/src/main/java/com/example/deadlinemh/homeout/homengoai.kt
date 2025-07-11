package com.example.deadlinemh.homengoai

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.deadlinemh.R
import kotlinx.coroutines.delay

@Composable
fun HomeHome(navController: NavHostController) {
    LaunchedEffect(Unit) {
        delay(500)
        navController.navigate("homeMain") {
            popUpTo("welcome") { inclusive = true }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Brngoai()
        LogoNgoai()
    }
}
@Composable
fun LogoNgoai() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.anhlogo),
            contentDescription = "anhlogo",
            modifier = Modifier.size(300.dp)
        )
    }
}
@Composable
fun Brngoai(){
    Image(painterResource(id = R.drawable.anhnen),
        contentDescription = "anh nen",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

