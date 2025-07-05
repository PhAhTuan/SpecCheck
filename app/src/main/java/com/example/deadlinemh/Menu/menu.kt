package com.example.deadlinemh.Menu



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun MenuApp(onClose:() -> Unit, navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.7f)
            .padding(top = 102.dp)
            .background(color = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Danh mục",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp))
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_close_clear_cancel),
                contentDescription = "Đóng",
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onClose() }
                    .padding(end = 8.dp, top = 8.dp)
            )
        }
        MenuItem("Laptop, Macbook", listOf("Lenovo","Asus","Acer","MacBook Pro"), navController)
        MenuItem("Linh kiện máy tính", listOf("Ram","SSD"), navController)
        MenuItem("Tản nhiệt", listOf("Tản nước","Tản khí"),navController)
        MenuItem("Màn hình, Arm", listOf("Man Samsung", "Man Benq", "Man LG", "Arm Humation"),navController)
        MenuItem("Gear", listOf("Ban phim", "Chuot", "Tai nghe", "Mic"),navController)
    }
}

@Composable
fun MenuItem(title: String, danhsachcon: List<String> = emptyList(), navController: NavController) {
    val moRong = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .padding(start = 12.dp)
            .clickable { moRong.value = !moRong.value },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = android.R.drawable.arrow_down_float),
            contentDescription = "Mũi tên",
            modifier = Modifier
                .size(24.dp)
                .padding(end = 8.dp)
                .clickable { moRong.value = !moRong.value }
        )
    }
    if (moRong.value) {
        danhsachcon.forEach { mucCon ->
            MenuItemCon(title = mucCon, navController = navController)
        }
    }
}


@Composable
fun MenuItemCon(title: String, navController: NavController){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .padding(start = 24.dp)
            .clickable{
                navController.navigate("menucon")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 14.sp, color = Color.Gray)
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Xemmenu(){
    val navController = rememberNavController()
    MenuApp(onClose = {}, navController = navController)
}