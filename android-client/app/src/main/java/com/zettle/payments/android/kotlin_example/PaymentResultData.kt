package com.zettle.payments.android.kotlin_example

import com.zettle.sdk.feature.cardreader.payment.Transaction
import com.zettle.sdk.feature.cardreader.payment.refunds.CardPaymentPayload
import com.zettle.sdk.feature.cardreader.payment.refunds.RefundPayload

import java.text.NumberFormat
import java.util.Locale
import kotlin.math.abs

data class PaymentResultData(
    val title: String,
    val amount: String,
    val reference: String?,
    val resultList: List<String>,
    val resultType: ResultType = ResultType.PAYMENT
)

enum class ResultType {
    PAYMENT,
    REFUND
}

fun Transaction.ResultPayload.toPaymentResultData(): PaymentResultData {
    return PaymentResultData(
        title = "Card Payment",
        amount = formatPaymentAmount(amount),
        reference = reference?.id,
        resultList = toResultListItems()
    )
}

fun RefundPayload.toPaymentResultData(): PaymentResultData {
    return PaymentResultData(
        title = "Card Refund",
        amount = formatPaymentAmount(-1 * abs(refundedAmount)),
        reference = null,
        resultList = toResultListItems(),
        resultType = ResultType.REFUND
    )
}

fun CardPaymentPayload.toPaymentResultData(): PaymentResultData {
    return PaymentResultData(
        title = "Card Payment",
        amount = formatPaymentAmount(amount),
        reference = referenceId,
        resultList = toResultListItems()
    )
}

fun Transaction.ResultPayload.toResultListItems(): List<String> {
    return listOf(
        "Status: ${status}",
        "Amount: ${amount}",
        "Reference: ${reference?.id}"
    )
}

fun RefundPayload.toResultListItems(): List<String> {
    return listOf(
        "Status: ${status}",
        "Refunded Amount: ${refundedAmount}",
        "Original Payment Id: ${originalPaymentReferenceId}"
    )
}

fun CardPaymentPayload.toResultListItems(): List<String> {
    return listOf(
        "Amount: ${amount}",
        "Reference Id: ${referenceId}",
        "Payment Method: ${paymentMethod}"
    )
}

fun formatPaymentAmount(amount: Long): String {
    val format = NumberFormat.getCurrencyInstance(Locale.UK)
    return format.format(amount / 100.0)
}


