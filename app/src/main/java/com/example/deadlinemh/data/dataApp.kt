package com.example.deadlinemh.data

import androidx.annotation.DrawableRes


data class Product(
    val id: Int,
    @DrawableRes val imageResId: Int,
    val name: String,
    val specs: String,
    val priceOld: String,
    val priceNew: String,

    val cpu: String,
    val ram: String,
    val ssd: String,
    val vga: String,
    val infor: String

)

data class ProductGroup(
    val title: String,
    val products: List<Product>
)
