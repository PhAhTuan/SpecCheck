package com.example.deadlinemh.interfaecSS


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.deadlinemh.data.Product
import com.example.deadlinemh.interfaceApp.getFakeProductGroups
import com.example.deadlinemh.interfaceApp.phantrencung
import com.example.deadlinemh.menu.MenuApp

@Composable
fun HomeScreenSS(navController: NavController, leftProductId: Int? = null, rightProductId: Int? = null) {
    val showMenu = remember { mutableStateOf(false) }
    val leftProduct = leftProductId?.let { id ->
        getFakeProductGroups(navController)
            .flatMap { it.products }
            .find { it.id == id }
    }
    val rightProduct = rightProductId?.let { id ->
        getFakeProductGroups(navController)
            .flatMap { it.products }
            .find { it.id == id }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        phantrencung(onMenuClick = { showMenu.value = true })
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp, start = 8.dp),
            verticalAlignment = Alignment.CenterVertically) {

            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "back",
                modifier = Modifier
                    .rotate(-90f)
                    .size(28.dp)
                    .clickable{navController.popBackStack()}
            )

            Spacer(modifier = Modifier.fillMaxWidth(0.2f))

            Text(
                text = "So sánh sản phẩm",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                //traiiiiiiiiii
                if (leftProduct != null) {
                    ProductSummary(leftProduct)
                } else {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Thêm",
                        modifier = Modifier.size(36.dp),
                        tint = Color(0xFF2196F3)
                    )
                    Text(
                        text = "Vui lòng chọn 1\nsản phẩm",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

            }
            VerticalDivider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.fillMaxHeight()
            )
            //phaiiiiiiiiiii
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (rightProduct != null) {
                    ProductSummary(rightProduct)
                } else {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Thêm",
                        modifier = Modifier
                            .size(36.dp)
                            .clickable{
                                navController.navigate("trangchu/$leftProductId")
                            },

                        tint = Color(0xFF2196F3)
                    )
                    Text(
                        text = "Vui lòng chọn 1\nsản phẩm",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
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
fun ProductSummary(product: Product) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = product.imageResId),
            contentDescription = product.name,
            modifier = Modifier
                .size(100.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = product.name,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Giá: ${product.priceNew}",
            color = Color.Blue,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "CPU: ${product.cpu}",
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "RAM: ${product.ram}",
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "SSD: ${product.ssd}",
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "VGA: ${product.vga}",
            fontSize = 16.sp
        )
    }
}






@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShowHomeScreenSS(){
    val navController = rememberNavController()
    HomeScreenSS(navController)
}