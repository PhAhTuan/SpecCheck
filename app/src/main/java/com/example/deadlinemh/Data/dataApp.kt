package com.example.deadlinemh.Data


data class Product(
    val imageUrl: String,
    val name: String,
    val specs: String,
    val priceOld: String,
    val priceNew: String
)

data class ProductGroup(
    val title: String,
    val products: List<Product>
)
