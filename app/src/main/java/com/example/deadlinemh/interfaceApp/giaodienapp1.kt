package com.example.deadlinemh.interfaceApp



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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart

import androidx.compose.ui.draw.clip
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
import com.example.deadlinemh.data.Product
import com.example.deadlinemh.data.ProductGroup
import com.example.deadlinemh.menu.MenuApp


@Composable
fun HomeScreenApp1(navController: NavController, leftProductId: Int? = null) {
    val selectedProduct = remember { mutableStateOf<Product?>(null) }
    val showMenu = remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        Column() {
            phantrencung(onMenuClick = { showMenu.value = true })
            LazyColumn {
                item { AnhBanner() }
                items(getFakeProductGroups(navController)) { group ->
                    ProductGroupSection(group, navController, leftProductId)
                  }
                }
            }
        Thanhtaskbar(modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter), navController) // Căn chỉnh ở dưới cùng

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
                contentDescription ="com/example/deadlinemh/menu",
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
fun ProductCard(product: Product, modifier: Modifier = Modifier, onClick: (Int) -> Unit ) {

    Column(
        modifier = modifier
            .padding(8.dp)
            .width(160.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
            .clickable { onClick(product.id) }
    ) {
        Box(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .background(Color.LightGray)
        ) {
            Image(
                painter = painterResource(id = product.imageResId),
                contentDescription = product.name,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(product.name, fontSize = 14.sp, maxLines = 2)
        Text(product.specs, fontSize = 12.sp, color = Color.Gray, maxLines = 2)

        Spacer(modifier = Modifier.height(4.dp))

        Column {
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
fun ProductGroupSection(group: ProductGroup, navController: NavController, leftProductId: Int? = null) {
    Column(modifier = Modifier.padding(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(group.title, fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            Text("Xem tất cả", fontSize = 12.sp, color = Color.Blue, modifier = Modifier.clickable{navController.navigate("xemtatca") })
        }

        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {
            items(group.products) { product ->
                ProductCard(product = product) { productId ->
                    if (leftProductId != null) {
                        navController.navigate("compare/$leftProductId/$productId")
                    } else {
                        navController.navigate("chitietsanpham/$productId")
                    }
                }
            }
        }
    }
}
fun getFakeProductGroups(navController: NavController): List<ProductGroup> {
    return listOf(
        ProductGroup(
            title = "Top mẫu laptop bán chạy tháng 7/2025",
            products = listOf(
                Product(id=1,R.drawable.legion5, "Legion 5 2024", "R7-8745H 16GB RAM 512GB SSD RTX 4050", "25,900,000đ", "21,900,000đ", cpu = "R7-8745H", ram = "16GB", ssd = "512GB", vga = "RTX 4050",
                    infor ="CPU: Intel Core i7 13650HX (14 nhân 20 luồng, turbo boost, 24MB Intel® Smart Cache)\n" +
                            "            RAM: 24GB\n" +
                            "            SSD: 512GB\n" +
                            "            Độ phân giải: 15.6\" FHD (1920x1080) IPS, 144Hz, 100% sRGB\n" +
                            "            VGA: Intel UHD Graphics cho gen 14 và NVIDIA® GeForce® RTX™ 4060 8GB GDDR6\n" +
                            "            Cổng kết nối:\n" +
                            "            - Kết nối không dây: 2x USB 3.2 Gen 1\n" +
                            "            - 1x USB 3.2 Gen 1 (Always On)\n" +
                            "            - 1x HDMI, up to 8K/60Hz\n" +
                            "            - 1x Ethernet (RJ-45)\n" +
                            "            Sạc: 230W"),
                Product(id=2,R.drawable.xiaoxin14c, "Xiaoxin 14c 2025", "R7-8745HS 16GB RAM 512GB SSD", "15,500,000đ", "15,500,000đ",cpu = "R7-8745HS", ram = "16GB", ssd = "512GB", vga = "AMD 780M",
                    infor = "CPU: AMD Ryzen 7 8845HS (8 nhân 16 luồng, turbo boost lên đến 5.1GHz, 16MB L3 Cache)\n" +
                            "RAM: 16GB LPDDR5x\n" +
                            "SSD: 1TB NVMe PCIe 4.0\n" +
                            "Độ phân giải: 14\" 2.8K (2880x1800) OLED, 120Hz, 100% DCI-P3, 400 nits\n" +
                            "VGA: AMD Radeon 780M\n" +
                            "Cổng kết nối:\n" +
                            "\n" +
                            "1x USB4\n" +
                            "\n" +
                            "1x USB 3.2 Gen 1 (Always On)\n" +
                            "\n" +
                            "1x USB 3.2 Gen 1\n" +
                            "\n" +
                            "1x HDMI 2.1\n" +
                            "\n" +
                            "1x Đầu đọc thẻ SD\n" +
                            "\n" +
                            "1x Jack tai nghe 3.5mm\n" +
                            "Sạc: 65W USB-C"),
                Product(id=3,R.drawable.thinkbookg14, "Thinkbook 14 G7 2025", "AI 7 H350 32GB RAM 1TB SSD", "21,800,000đ", "21,800,000đ",cpu = "R7 AI H350", ram = "32GB", ssd = "1TB", vga = "AMD 860M",
                    infor="CPU: Intel Core Ultra 7 155H (16 nhân 22 luồng, turbo boost lên đến 4.8GHz, 24MB Cache)\n" +
                            "RAM: 32GB LPDDR5x\n" +
                            "SSD: 1TB NVMe PCIe 4.0\n" +
                            "Độ phân giải: 14.5\" 3K (3072x1920) IPS, 120Hz, 100% DCI-P3, 400 nits\n" +
                            "VGA: Intel Arc Graphics\n" +
                            "Cổng kết nối:\n" +
                            "\n" +
                            "1x Thunderbolt 4\n" +
                            "\n" +
                            "1x USB-C 3.2 Gen 2\n" +
                            "\n" +
                            "2x USB 3.2 Gen 1\n" +
                            "\n" +
                            "1x HDMI 2.1\n" +
                            "\n" +
                            "1x Ethernet (RJ-45)\n" +
                            "\n" +
                            "1x Đầu đọc thẻ MicroSD\n" +
                            "Sạc: 100W USB-C")
            )
        ),
        ProductGroup(
            title = "ASUS",
            products = listOf(
                Product(id=4,R.drawable.vivo15x, "ASUS Vivobook 15X OLED", "i5-12500H 16GB RAM 512GB SSD", "18,490,000đ", "16,990,000đ", cpu = "i5-12700H", ram = "8GB", ssd = "512GB", vga = "Intel Iris Xe Graphics",
                    infor="CPU: Intel Core i7-1360P (12 nhân 16 luồng, turbo boost lên đến 5.0GHz, 18MB Cache)\n" +
                            "RAM: 16GB DDR4\n" +
                            "SSD: 512GB NVMe PCIe 4.0\n" +
                            "Độ phân giải: 15.6\" 2.8K (2880x1620) OLED, 120Hz, 100% DCI-P3\n" +
                            "VGA: Intel Iris Xe Graphics\n" +
                            "Cổng kết nối:\n" +
                            "\n" +
                            "1x USB 3.2 Gen 1 Type-C\n" +
                            "\n" +
                            "2x USB 3.2 Gen 1 Type-A\n" +
                            "\n" +
                            "1x USB 2.0 Type-A\n" +
                            "\n" +
                            "1x HDMI 1.4\n" +
                            "\n" +
                            "1x Jack tai nghe 3.5mm\n" +
                            "Sạc: 65W"),
                Product(id=5,R.drawable.tuff15, "ASUS TUF Gaming F15", "i7-12700H 16GB RAM 512GB SSD RTX 4060", "29,990,000đ", "27,490,000đ", cpu = "i7-12700H", ram = "16GB", ssd = "512GB", vga = "RTX 4050",
                    infor="CPU: Intel Core i7-13620H (10 nhân 16 luồng, turbo boost lên đến 4.9GHz, 24MB Cache)\n" +
                            "RAM: 16GB DDR5 4800MHz\n" +
                            "SSD: 512GB NVMe PCIe 4.0\n" +
                            "Độ phân giải: 15.6\" FHD (1920x1080) IPS, 144Hz, 100% sRGB, G-Sync\n" +
                            "VGA: Intel UHD Graphics và NVIDIA® GeForce® RTX™ 4060 8GB GDDR6\n" +
                            "Cổng kết nối:\n" +
                            "\n" +
                            "1x Thunderbolt 4 (hỗ trợ DisplayPort)\n" +
                            "\n" +
                            "1x USB 3.2 Gen 2 Type-C (hỗ trợ DisplayPort, G-SYNC)\n" +
                            "\n" +
                            "2x USB 3.2 Gen 1 Type-A\n" +
                            "\n" +
                            "1x HDMI 2.1\n" +
                            "\n" +
                            "1x Ethernet (RJ-45)\n" +
                            "Sạc: 240W"),
                Product(id=6,R.drawable.zenbook14, "ASUS Zenbook 14 OLED", "R7-7730U 16GB RAM 1TB SSD", "24,990,000đ", "22,990,000đ", cpu = "Intel Ult 7 155H", ram = "16GB", ssd = "1TB", vga = "Intel Arc Graphics",
                    infor="CPU: Intel Core Ultra 7 155H (16 nhân 22 luồng, turbo boost lên đến 4.8GHz, 24MB Cache)\n" +
                            "RAM: 16GB LPDDR5x\n" +
                            "SSD: 1TB NVMe PCIe 4.0\n" +
                            "Độ phân giải: 14\" 3K (2880x1800) OLED, 120Hz, 100% DCI-P3\n" +
                            "VGA: Intel Arc Graphics\n" +
                            "Cổng kết nối:\n" +
                            "\n" +
                            "2x Thunderbolt 4 (hỗ trợ hiển thị / sạc)\n" +
                            "\n" +
                            "1x USB 3.2 Gen 1 Type-A\n" +
                            "\n" +
                            "1x HDMI 2.1\n" +
                            "\n" +
                            "1x Jack tai nghe 3.5mm\n" +
                            "Sạc: 65W USB-C")
            )
        ),
                ProductGroup(
            title = "Dell",
            products = listOf(
                Product(id=7,R.drawable.dell_inspiron_15_3520_, "Dell Inspiron 15 3530", "i5-1335U 8GB RAM 512GB SSD", "17,990,000đ", "15,490,000đ", cpu = "R7-8745H", ram = "16GB", ssd = "512GB", vga = "RTX 4050",
                    infor="CPU: Intel Core i7-1355U (10 nhân 12 luồng, turbo boost lên đến 5.0GHz, 12MB Cache)\n" +
                            "RAM: 16GB DDR4 3200MHz\n" +
                            "SSD: 512GB NVMe\n" +
                            "Độ phân giải: 15.6\" FHD (1920x1080) WVA, 120Hz, Chống chói\n" +
                            "VGA: Intel Iris Xe Graphics\n" +
                            "Cổng kết nối:\n" +
                            "\n" +
                            "1x USB 3.2 Gen 1 Type-C (chỉ truyền dữ liệu)\n" +
                            "\n" +
                            "1x USB 3.2 Gen 1\n" +
                            "\n" +
                            "1x USB 2.0\n" +
                            "\n" +
                            "1x HDMI 1.4\n" +
                            "\n" +
                            "1x Đầu đọc thẻ SD\n" +
                            "Sạc: 65W"),
                Product(id=8,R.drawable.vostro14, "Dell Vostro 14 3430", "i3-1315U 8GB RAM 256GB SSD", "13,990,000đ", "12,490,000đ", cpu = "i5-1335U", ram = "16GB", ssd = "512GB", vga = "Intel Iris Xe Graphics",
                    infor="CPU: Intel Core i5-1335U (10 nhân 12 luồng, turbo boost lên đến 4.6GHz, 12MB Cache)\n" +
                            "RAM: 16GB DDR4\n" +
                            "SSD: 512GB NVMe\n" +
                            "Độ phân giải: 14\" FHD (1920x1080) WVA, 60Hz, Chống chói\n" +
                            "VGA: Intel Iris Xe Graphics và NVIDIA® GeForce® MX550 2GB GDDR6\n" +
                            "Cổng kết nối:\n" +
                            "\n" +
                            "1x USB 3.2 Gen 1 Type-C (hỗ trợ DisplayPort)\n" +
                            "\n" +
                            "1x USB 3.2 Gen 1\n" +
                            "\n" +
                            "1x USB 2.0\n" +
                            "\n" +
                            "1x HDMI 1.4\n" +
                            "\n" +
                            "1x Ethernet (RJ-45)\n" +
                            "Sạc: 65W"),
                Product(id=9,R.drawable.dell_inspiron_15_3520_, "Dell XPS 13 Plus 9320", "i7-1360P 16GB RAM 1TB SSD", "48,990,000đ", "45,990,000đ", cpu = "i5-1335U", ram = "16GB", ssd = "512GB", vga = "NVIDIA MX550",
                    infor="CPU: Intel Core i5-1335U (10 nhân 12 luồng, turbo boost lên đến 4.6GHz, 12MB Cache)\n" +
                            "RAM: 16GB DDR4\n" +
                            "SSD: 512GB NVMe\n" +
                            "Độ phân giải: 14\" FHD (1920x1080) WVA, 60Hz, Chống chói\n" +
                            "VGA: Intel Iris Xe Graphics và NVIDIA® GeForce® MX550 2GB GDDR6\n" +
                            "Cổng kết nối:\n" +
                            "\n" +
                            "1x USB 3.2 Gen 1 Type-C (hỗ trợ DisplayPort)\n" +
                            "\n" +
                            "1x USB 3.2 Gen 1\n" +
                            "\n" +
                            "1x USB 2.0\n" +
                            "\n" +
                            "1x HDMI 1.4\n" +
                            "\n" +
                            "1x Ethernet (RJ-45)\n" +
                            "Sạc: 65W")
            )
        )
    )
}

@Composable
fun Thanhtaskbar(modifier: Modifier = Modifier, navController: NavController){
    Row(
        modifier = modifier
        .fillMaxWidth()
        .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
        .background(Color(0xFFE0E0E0)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Trang chủ",
                tint = Color.Gray,
                modifier = Modifier
                    .size(32.dp)
                    .clickable{
                        navController.navigate("homethanhtaskbar")
                    }
            )
            Text(
                text = "Trang chủ",
                color = Color.Black,
                fontSize = 12.sp
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Giỏ hàng",
                tint = Color.Gray,
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = "Giỏ hàng",
                color = Color.Black,
                fontSize = 12.sp
            )
        }


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable { navController.navigate("account") },) {

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Cá nhân",
                tint = Color.Gray,
                modifier = Modifier.size(32.dp)

            )
            Text(
                text = "Cá nhân",
                color = Color.Black,
                fontSize = 12.sp
            )
        }
    }
}





@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreviewApp(){
    val navController = rememberNavController()
    HomeScreenApp1(navController)
}










