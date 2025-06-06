package com.zettle.payments.android.kotlin_example

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PaymentResultBottomSheet : BottomSheetDialogFragment() {

    private lateinit var doneButton: Button
    private lateinit var titleTextView: TextView
    private lateinit var amountTextView: TextView
    private lateinit var referenceTextView: TextView
    private lateinit var paymentResultList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_payment_result, container, false)
        doneButton = view.findViewById(R.id.doneBtn)
        titleTextView = view.findViewById(R.id.titleTxt)
        amountTextView = view.findViewById(R.id.amountTxt)
        referenceTextView = view.findViewById(R.id.referenceTxt)
        paymentResultList = view.findViewById(R.id.paymentResultList)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doneButton.setOnClickListener { dismiss() }

        val data = arguments?.getParcelable<PaymentResultData>(PAYMENT_RESULT_KEY)
        initView(data)
    }

    private fun initView(paymentResultData: PaymentResultData?) {
        paymentResultData ?: return

        titleTextView.text = paymentResultData.title
        amountTextView.text = paymentResultData.amount
        referenceTextView.text = paymentResultData.reference

        if (paymentResultData.resultType == ResultType.REFUND) {
            amountTextView.setTextColor(
                ContextCompat.getColor(requireContext(), android.R.color.holo_red_light)
            )
        }

        paymentResultList.apply {
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                    setDrawable(ColorDrawable(ContextCompat.getColor(context, R.color.divider)))
                }
            )
            adapter = PaymentListAdapter(paymentResultData.resultList)
        }
    }

    companion object {
        private const val PAYMENT_RESULT_KEY = "paymentResultDataKey"
        const val TAG = "PaymentResultBottomSheet"

        fun newInstance(paymentResultData: PaymentResultData): PaymentResultBottomSheet {
            return PaymentResultBottomSheet().apply {
                arguments = Bundle().apply {
                    putParcelable(PAYMENT_RESULT_KEY, paymentResultData)
                }
            }
        }
    }
}

private class PaymentListAdapter(
    private val resultItems: List<ResultItem>
) : RecyclerView.Adapter<PaymentListAdapter.ResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_payment_result_view, parent, false)
        return ResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val item = resultItems[position]
        holder.resultTitle.text = item.name
        holder.resultValue.text = item.value.orEmpty()
    }

    override fun getItemCount(): Int = resultItems.size

    class ResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val resultTitle: TextView = view.findViewById(R.id.resultTitleTxt)
        val resultValue: TextView = view.findViewById(R.id.resultValueTxt)
    }
}
