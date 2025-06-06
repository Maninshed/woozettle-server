package com.zettle.payments.android.kotlin_example.model

data class Product(
    val id: Int,
    val sku: String,
    val name: String,
    val price: String,
    val stock: Int?,
    val image: String,
    val categories: List<String>,
    val tags: List<String> // optional, if needed
)
