
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.deadlinemh.GiaodienApp.phantrencung
import com.example.deadlinemh.Menu.MenuApp
import com.example.deadlinemh.R



@Composable
fun HomeScreenApp2(navController: NavController){
    val showMenu = remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            phantrencung(onMenuClick = {showMenu.value = true })
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 102.dp)
        ) {
            item {
                LaptopDetailCard()
            }
        }
        //heloo
        AnimatedVisibility(
            visible = showMenu.value,
            enter = slideInHorizontally(initialOffsetX = { it }),
            exit = slideOutHorizontally(targetOffsetX = { it })
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                MenuApp(
                    onClose = { showMenu.value = false },
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun LaptopDetailCard() {
    Column(
        modifier = Modifier
            .padding(12.dp)
           // .padding(top = 64.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Legion 5 Y7000 2024 i7 13650HX",
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )

        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Giá tham khảo: 22,500,55đ",
            color = Color.Blue,
            fontSize = 14.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = R.drawable.legion5),
            contentDescription = "Laptop Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(165.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            InfoTag(label = "CPU", value = "i7-13650H")
            InfoTag(label = "RAM", value = "24GB")
            InfoTag(label = "SSD", value = "512GB")
            InfoTag(label = "VGA", value = "RTX4060")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            repeat(5) {
                Icon(Icons.Default.Star,
                    contentDescription = "Star",
                    tint = Color.Red,
                    modifier = Modifier.size(26.dp)
                    )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "So Sánh", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "THÔNG SỐ KỸ THUẬT",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = """
            CPU: Intel Core i7 13650HX (14 nhân 20 luồng, turbo boost, 24MB Intel® Smart Cache)
            RAM: 24GB
            SSD: 512GB
            Độ phân giải: 15.6" FHD (1920x1080) IPS, 144Hz, 100% sRGB
            VGA: Intel UHD Graphics cho gen 14 và NVIDIA® GeForce® RTX™ 4060 8GB GDDR6
            Cổng kết nối:
            - Kết nối không dây: 2x USB 3.2 Gen 1
            - 1x USB 3.2 Gen 1 (Always On)
            - 1x HDMI, up to 8K/60Hz
            - 1x Ethernet (RJ-45)
            Sạc: 230W
        """.trimIndent(),
            fontSize = 18.sp,
            color = Color.Black
        )
    }
}

@Composable
fun InfoTag(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
            .padding(4.dp)
    ) {
        Text(text = label, fontSize = 12.sp, color = Color.DarkGray)
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLaptopDetailCard() {
    val navController = rememberNavController()
    HomeScreenApp2(navController)
}




//hello hh