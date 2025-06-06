package com.zettle.payments.android.kotlin_example.api

import com.zettle.payments.android.kotlin_example.model.Category
import com.zettle.payments.android.kotlin_example.model.Product
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WooCommerceClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl(WooCredentials.BASE_URL) // must end with /
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(WooCommerceService::class.java)

    // ✅ Fetch product categories
    suspend fun getCategories(): List<Category> {
        return service.getCategories()
    }

    // ✅ Fetch product list
    suspend fun getProducts(): List<Product> {
        return service.getProducts()
    }
}





