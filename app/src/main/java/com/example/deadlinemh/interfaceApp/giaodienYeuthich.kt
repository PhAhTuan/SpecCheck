package com.example.deadlinemh.interfaceApp

import android.util.Log
import android.widget.Toast
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.deadlinemh.data.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSRFavorite(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current
    var favoriteProducts by remember { mutableStateOf<List<Product>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }


    LaunchedEffect(Unit) {
        val user = auth.currentUser
        if (user != null) {
            Log.d("HomeSRFavorite", "Loading favorites for user: ${user.uid}")
            db.collection("users").document(user.uid)
                .collection("favorites")
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        Log.e("HomeSRFavorite", "Error loading favorites: ${error.message}")
                        Toast.makeText(context, "Lỗi tải danh sách yêu thích: ${error.message}", Toast.LENGTH_SHORT).show()
                        isLoading = false
                        return@addSnapshotListener
                    }
                    val products = snapshots?.documents?.mapNotNull { it.toObject(Product::class.java) } ?: emptyList()
                    favoriteProducts = products
                    isLoading = false
                    Log.d("HomeSRFavorite", "Favorites loaded: ${products.size} items")
                }
        } else {
            isLoading = false
            Toast.makeText(context, "Vui lòng đăng nhập để xem danh sách yêu thích", Toast.LENGTH_SHORT).show()
            Log.d("HomeSRFavorite", "No user logged in")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Danh Sách Yêu Thích") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.KeyboardArrowDown,
                            contentDescription = "Back",
                            modifier = Modifier.graphicsLayer(rotationZ = 90f)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (favoriteProducts.isEmpty()) {
                Text(
                    text = "Chưa có sản phẩm yêu thích",
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            } else {
                LazyColumn {
                    items(favoriteProducts.size) { index ->
                        val product = favoriteProducts[index]
                        FavoriteProductCard(
                            product = product,
                            navController = navController,
                            onRemoveFavorite = {

                                auth.currentUser?.let { user ->
                                    db.collection("users").document(user.uid)
                                        .collection("favorites").document(product.id.toString())
                                        .delete()
                                        .addOnSuccessListener {
                                            favoriteProducts = favoriteProducts.filter { it.id != product.id }
                                        }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteProductCard(
    product: Product,
    navController: NavController,
    onRemoveFavorite: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("detail/${product.id}") },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = product.imageResId),
                contentDescription = product.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Giá: ${product.priceNew}",
                    fontSize = 14.sp,
                    color = Color.Blue
                )
            }
            IconButton(onClick = onRemoveFavorite) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = "Bỏ yêu thích",
                    tint = Color.Red,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}