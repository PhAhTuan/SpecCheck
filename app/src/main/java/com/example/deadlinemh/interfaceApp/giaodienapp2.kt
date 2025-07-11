import android.widget.Toast
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.deadlinemh.data.Product
import com.example.deadlinemh.interfaceApp.Phantrencung
import com.example.deadlinemh.interfaceApp.Thanhtaskbar
import com.example.deadlinemh.interfaceApp.getFakeProductGroups
import com.example.deadlinemh.menu.MenuApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@Composable
fun HomeScreenApp2(navController: NavController, productId: Int){
    val showMenu = remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val product = getFakeProductGroups(navController)
        .flatMap { it.products }
        .find { it.id == productId }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Phantrencung(
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                onMenuClick = {showMenu.value = true }
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 102.dp)
        ) {
            item {
                product?.let {
                    LaptopDetailCard(it, navController)
                } ?: Text("Không tìm thấy sản phẩm")
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
        Thanhtaskbar(modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter), navController)
    }
}

@Composable
fun LaptopDetailCard(product: Product, navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    var isFavorite by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(product.id) {
        auth.currentUser?.let { user ->
            db.collection("users").document(user.uid)
                .collection("favorites").document(product.id.toString())
                .get()
                .addOnSuccessListener { document ->
                    isFavorite = document.exists()
                    isLoading = false
                }
                .addOnFailureListener {
                    isLoading = false
                    Toast.makeText(context, "Lỗi tải trạng thái yêu thích", Toast.LENGTH_SHORT)
                        .show()
                }
        } ?: run {
            isLoading = false
        }
    }
    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = product.name,
                modifier = Modifier.padding(top = 8.dp),
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
            IconButton(
                onClick = {
                    auth.currentUser?.let { user ->
                        if (isFavorite) {
                            db.collection("users").document(user.uid)
                                .collection("favorites").document(product.id.toString())
                                .delete()
                                .addOnSuccessListener {
                                    isFavorite = false
                                    Toast.makeText(context, "Đã bỏ yêu thích", Toast.LENGTH_SHORT)
                                        .show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        context,
                                        "Lỗi khi bỏ yêu thích",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        } else {
                            db.collection("users").document(user.uid)
                                .collection("favorites").document(product.id.toString())
                                .set(product)
                                .addOnSuccessListener {
                                    isFavorite = true
                                    Toast.makeText(
                                        context,
                                        "Đã thêm vào yêu thích",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        context,
                                        "Lỗi khi thêm vào yêu thích",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    } ?: Toast.makeText(context, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show()
                },
                enabled = !isLoading
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Chọn yêu thích",
                    tint = if (isFavorite) Color.Red else Color.Gray,
                    modifier = Modifier
                        .padding(top = 8.dp, end = 8.dp)
                        .size(32.dp)
                )
            }
        }
        Text(
            text = "Giá tham khảo: ${product.priceNew}",
            color = Color.Blue,
            fontSize = 14.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = product.imageResId),
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
            InfoTag(label = "CPU", value = product.cpu)
            InfoTag(label = "RAM", value = product.ram)
            InfoTag(label = "SSD", value = product.ssd)
            InfoTag(label = "VGA", value = product.vga)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            repeat(5) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = "Star",
                    tint = Color.Red,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                navController.navigate("compare/${product.id}")
            },
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
            text = product.infor.trimIndent(),
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
    HomeScreenApp2(navController, productId = 1)
}