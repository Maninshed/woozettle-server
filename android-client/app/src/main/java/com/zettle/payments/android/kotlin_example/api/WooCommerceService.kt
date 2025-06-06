package com.zettle.payments.android.kotlin_example.api

import com.zettle.payments.android.kotlin_example.model.Category
import com.zettle.payments.android.kotlin_example.model.Product
import retrofit2.http.GET

interface WooCommerceService {

    @GET("categories")
    suspend fun getCategories(): List<Category>

    @GET("products")
    suspend fun getProducts(): List<Product>
}






