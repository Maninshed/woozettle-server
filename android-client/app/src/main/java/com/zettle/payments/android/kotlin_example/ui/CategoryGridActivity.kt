

package com.zettle.payments.android.kotlin_example.ui

import com.zettle.payments.android.kotlin_example.ui.ProductGridActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zettle.payments.android.kotlin_example.R
import com.zettle.payments.android.kotlin_example.api.WooCommerceClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryGridActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_grid)

        recyclerView = findViewById(R.id.recyclerViewCategories)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        loadCategories()
    }

    private fun loadCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val categories = WooCommerceClient.getCategories()
                withContext(Dispatchers.Main) {
                    recyclerView.adapter = CategoryAdapter(categories) { category ->
                        // Open ProductGridActivity with category filter
                        val intent = Intent(this@CategoryGridActivity, ProductGridActivity::class.java)
                        intent.putExtra("category_name", category.name)
                        startActivity(intent)
                    }
                }
            } catch (e: Exception) {
                Log.e("WooAPI", "Failed to load categories: ${e.message}")
            }
        }
    }
}
