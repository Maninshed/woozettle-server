package com.zettle.payments.android.kotlin_example

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.zettle.sdk.feature.cardreader.model.Amount
import com.zettle.sdk.feature.cardreader.ZettleCardReader
import com.zettle.sdk.feature.cardreader.ZettleCardReaderResult

import java.util.*

class ManualCardEntryActivity : AppCompatActivity() {

    private lateinit var chargeButton: Button
    private lateinit var amountInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_card_entry)

        chargeButton = findViewById(R.id.charge_btn)
        amountInput = findViewById(R.id.amount_input)

        chargeButton.setOnClickListener {
            handleCharge()
        }
    }

    private fun handleCharge() {
        val amountValue = amountInput.text.toString().toLongOrNull()
        if (amountValue == null || amountValue <= 0) {
            showSnackBar("Please enter a valid amount")
            return
        }

        val traceId = UUID.randomUUID().toString()
        val amount = Amount.ofMinor("SEK", amountValue)

        ZettleManualCardEntry.getInstance().charge(
            amount = amount,
            traceId = traceId,
            context = this
        ) { result ->
            when (result) {
                is ZettleManualCardEntryResult.Completed -> {
                    showSnackBar("Charge successful: ${result.amount.amount} ${result.amount.currency}")
                }
                is ZettleManualCardEntryResult.Cancelled -> {
                    showSnackBar("Charge was cancelled")
                }
                is ZettleManualCardEntryResult.Failed -> {
                    showSnackBar("Charge failed: ${result.reason}")
                }
            }
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }
}

