package com.example.deadlinemh.GiaodienApp



import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.deadlinemh.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.deadlinemh.Data.Product
import com.example.deadlinemh.Data.ProductGroup
import com.example.deadlinemh.Menu.MenuApp


@Composable
fun HomeScreenApp1(navController: NavController) {
    val selectedProduct = remember { mutableStateOf<Product?>(null) }
    val showMenu = remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        Column()
        {
            phantrencung(onMenuClick = { showMenu.value = true })
            LazyColumn {
                item { AnhBanner() }
                items(getFakeProductGroups(navController)) { group ->
                    ProductGroupSection(group, navController)
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
                selectedProduct.value?.let { product ->
                    Text(
                        text = "Đã chọn: ${product.name}",
                        modifier = Modifier
                            .padding(16.dp)
                            .background(Color.Yellow)
                    )
                }
            }
        }
    }
@Composable
fun phantrencung(onMenuClick: () -> Unit){
    var timkiem = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF4FB3BF))
            .padding(12.dp)
            .padding(top = 38.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            OutlinedTextField(
                value = timkiem.value,
                onValueChange = {timkiem.value = it},
                placeholder = {Text(text = "Hãy nhập tìm kiếm", color = Color.Black)},
                shape = RoundedCornerShape(46.dp),
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "tim kiem")
                },
                modifier = Modifier
                    .padding(start = 18.dp)
                    .height(52.dp)
                    .fillMaxWidth(0.8f)

                )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                Icons.Default.Menu,
                contentDescription ="com/example/deadlinemh/Menu",
                tint = Color.White,
                modifier = Modifier
                    .size(52.dp)
                    .clickable { onMenuClick() }

            )
        }
    }
}
@Composable
fun AnhBanner() {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(0.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(2) {index ->
            Image(
                painter = painterResource(
                    id = if (index == 0) R.drawable.bannerr else R.drawable.anhhome1),
                contentDescription = "banner $index",
                modifier = Modifier
                    .height(250.dp),
                contentScale = ContentScale.FillHeight

            )
        }
    }
}

@Composable
fun ProductCard(product: Product, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .width(160.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .background(Color.LightGray)
        ) {
            Text("Ảnh", modifier = Modifier.align(Alignment.Center))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(product.name, fontSize = 14.sp, maxLines = 2)
        Text(product.specs, fontSize = 12.sp, color = Color.Gray, maxLines = 2)

        Spacer(modifier = Modifier.height(4.dp))

        Row {
            Text(
                product.priceOld,
                fontSize = 12.sp,
                color = Color.Gray,
                textDecoration = TextDecoration.LineThrough
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(product.priceNew, fontSize = 14.sp, color = Color.Red)
        }
    }
}
@Composable
fun ProductGroupSection(group: ProductGroup, navController: NavController) {
    Column(modifier = Modifier.padding(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(group.title, fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            Text("Xem tất cả", fontSize = 12.sp, color = Color.Blue)
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow {
            items(group.products) { product ->
                ProductCard(product = product, onClick = {navController.navigate("chitietsanpham")})
            }
        }
    }
}
fun getFakeProductGroups(navController: NavController): List<ProductGroup> {
    return listOf(
        ProductGroup(
            title = "Top mẫu laptop bán chạy tháng 7/2025",
            products = listOf(
                Product("link_ảnh_1", "Legion 5 2024", "R7-8745H 16GB RAM 512GB SSD RTX 4050", "25,900,000đ", "21,900,000đ"),
                Product("link_ảnh_2", "Xiaoxin 14c 2025", "R7-8745HS 16GB RAM 512GB SSD", "15,500,000đ", "15,500,000đ"),
                Product("link_ảnh_3", "Thinkbook 14 G7 2025", "AI 7 H350 32GB RAM 1TB SSD", "21,800,000đ", "21,800,000đ")
            )
        ),
        ProductGroup(
            title = "Lenovo",
            products = listOf(
                Product("link_ảnh_4", "Legion 5 2024", "R7-8745H 16GB RAM 512GB SSD RTX 4050", "25,900,000đ", "21,900,000đ"),
                Product("link_ảnh_5", "Xiaoxin 14c 2025", "R7-8745HS 16GB RAM 512GB SSD", "15,500,000đ", "15,500,000đ"),
                Product("link_ảnh_6", "Thinkbook 14 G7 2025", "AI 7 H350 32GB RAM 1TB SSD", "21,800,000đ", "21,800,000đ")
            )
        ),
        ProductGroup(
            title = "Acer",
            products = listOf(
                Product("link_ảnh_4", "Legion 5 2024", "R7-8745H 16GB RAM 512GB SSD RTX 4050", "25,900,000đ", "21,900,000đ"),
                Product("link_ảnh_5", "Xiaoxin 14c 2025", "R7-8745HS 16GB RAM 512GB SSD", "15,500,000đ", "15,500,000đ"),
                Product("link_ảnh_6", "Thinkbook 14 G7 2025", "AI 7 H350 32GB RAM 1TB SSD", "21,800,000đ", "21,800,000đ")
            )
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreviewApp(){
    val navController = rememberNavController()
    HomeScreenApp1(navController)
}










//@Composable
//fun ProductScreen() {
//    val groups = listOf(
//        ProductGroup(
//            title = "Top mẫu laptop bán chạy tháng 7/2025",
//            products = listOf(
//                Product("link_ảnh_1", "Legion 5 2024", "R7-8745H 16GB RAM 512GB SSD RTX 4050", "25,900,000đ", "21,900,000đ"),
//                Product("link_ảnh_2", "Xiaoxin 14c 2025", "R7-8745HS 16GB RAM 512GB SSD", "15,500,000đ", "15,500,000đ"),
//                Product("link_ảnh_3", "Thinkbook 14 G7 2025", "AI 7 H350 32GB RAM 1TB SSD", "21,800,000đ", "21,800,000đ")
//            )
//        ),
//        ProductGroup(
//            title = "Lenovo",
//            products = listOf(
//                Product("link_ảnh_4", "Legion 5 2024", "R7-8745H 16GB RAM 512GB SSD RTX 4050", "25,900,000đ", "21,900,000đ"),
//                Product("link_ảnh_5", "Xiaoxin 14c 2025", "R7-8745HS 16GB RAM 512GB SSD", "15,500,000đ", "15,500,000đ"),
//                Product("link_ảnh_6", "Thinkbook 14 G7 2025", "AI 7 H350 32GB RAM 1TB SSD", "21,800,000đ", "21,800,000đ")
//            )
//        )
//    )
//
//    LazyColumn(modifier = Modifier.fillMaxSize()) {
//        items(groups) { group ->
//            ProductGroupSection(group)
//        }
//    }
//}