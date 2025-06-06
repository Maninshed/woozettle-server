package com.zettle.payments.android.kotlin_example

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.zettle.sdk.feature.cardreader.Amount
import com.zettle.sdk.feature.cardreader.ZettleCardReader
import com.zettle.sdk.feature.cardreader.ZettleCardReaderResult
import java.util.*

class CardReaderActivity : AppCompatActivity() {

    private lateinit var chargeBtn: Button
    private var lastTraceId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_reader)

        chargeBtn = findViewById(R.id.card_charge_btn)

        chargeBtn.setOnClickListener {
            val traceId = UUID.randomUUID().toString()
            lastTraceId = traceId

            ZettleCardReader.getInstance().charge(
                context = this,
                amount = Amount.ofMinor("GBP", 1000),
                traceId = traceId
            ) { result ->
                when (result) {
                    is ZettleCardReaderResult.Completed -> {
                        showSnackBar("Charged: ${result.amount.amount} ${result.amount.currency}")
                    }
                    is ZettleCardReaderResult.Cancelled -> {
                        showSnackBar("Cancelled by user")
                    }
                    is ZettleCardReaderResult.Failed -> {
                        showSnackBar("Failed: ${result.reason}")
                    }
                }
            }
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }
}




