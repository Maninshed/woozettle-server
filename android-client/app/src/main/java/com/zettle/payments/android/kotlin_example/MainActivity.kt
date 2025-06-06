package com.zettle.payments.android.kotlin_example

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zettle.payments.android.kotlin_example.databinding.ActivityMainBinding
import com.zettle.payments.android.kotlin_example.ui.CategoryGridActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isLoggedIn = false // Simulated login state for UI feedback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        updateLoginStateText()
    }

    private fun setupListeners() {
        binding.loginBtn.setOnClickListener {
            isLoggedIn = true
            updateLoginStateText()
        }

        binding.logoutBtn.setOnClickListener {
            isLoggedIn = false
            updateLoginStateText()
        }

        binding.openInventoryBtn.setOnClickListener {
            startActivity(Intent(this, CategoryGridActivity::class.java))
        }

        binding.openCardReaderBtn.setOnClickListener {
            startActivity(Intent(this, CardReaderActivity::class.java))
        }

        binding.openMceBtn.setOnClickListener {
            startActivity(Intent(this, ManualCardEntryActivity::class.java))
        }

        // 🔥 Removed: PayPal/Venmo functionality no longer supported in SDK 2.24.2
        // binding.openPaypalBtn?.setOnClickListener { ... }
    }

    private fun updateLoginStateText() {
        val state = if (isLoggedIn) "Authenticated" else "Not Authenticated"
        binding.loginState.text = "State: $state"
    }
}


