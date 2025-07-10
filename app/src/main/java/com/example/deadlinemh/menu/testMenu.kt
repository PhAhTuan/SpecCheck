package com.example.deadlinemh.menu


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Icon


@Composable
fun testMenu(navController: NavController, onClose:() -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)

    ) {
        Text(
            text = "Hello Test Menu, Tuan dz", modifier = Modifier
                .size(100.dp)
        )
        Row (
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth()

        ){
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_close_clear_cancel),
                contentDescription = "dong lai",
                modifier = Modifier
                    .padding(12.dp)
                    .clickable{onClose()}
            )
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Testtest(){
    val navController = rememberNavController()
    testMenu(navController, onClose = {navController.popBackStack()})
}