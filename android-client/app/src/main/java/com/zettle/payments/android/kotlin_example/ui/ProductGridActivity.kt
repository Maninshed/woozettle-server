

package com.zettle.payments.android.kotlin_example.ui

import com.zettle.payments.android.kotlin_example.R // ✅ Correct R class

import com.zettle.payments.android.kotlin_example.ui.ProductAdapter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zettle.payments.android.kotlin_example.api.WooCommerceClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductGridActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_grid)

        recyclerView = findViewById(R.id.recyclerViewProducts)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        loadProducts()
    }

    private fun loadProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val products = WooCommerceClient.getProducts()


                withContext(Dispatchers.Main) {
                    recyclerView.adapter = ProductAdapter(products)
                }
            } catch (e: Exception) {
                Log.e("WooAPI", "Failed to load products: ${e.message}")
            }
        }
    }
}





