package com.example.deadlinemh.interfaceApp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(navController: NavController, categoryName: String) {
    val allProducts = remember { getFakeProductGroups(navController).flatMap { it.products } }

    val filteredProducts = remember(allProducts, categoryName) {
        if (categoryName.equals("Lenovo", ignoreCase = true)) {
            allProducts.filter { product ->
                product.name.contains("Legion", ignoreCase = true) ||
                        product.name.contains("Xiaoxin", ignoreCase = true) ||
                        product.name.contains("Thinkbook", ignoreCase = true)
            }
        } else {
            allProducts.filter { product ->
                product.name.contains(categoryName, ignoreCase = true) ||
                        product.specs.contains(categoryName, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(categoryName, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Quay lại")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            if (filteredProducts.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Không có sản phẩm nào trong danh mục này.")
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(filteredProducts) { product ->
                        SearchResultItem(product = product) {
                            navController.navigate("chitietsanpham/${product.id}")
                        }
                    }
                }
            }
        }
    }
}