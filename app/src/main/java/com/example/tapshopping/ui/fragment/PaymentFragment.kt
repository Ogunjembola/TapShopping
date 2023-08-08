package com.example.tapshopping.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import co.paystack.android.Paystack
import co.paystack.android.PaystackSdk
import co.paystack.android.Transaction
import co.paystack.android.exceptions.ExpiredAccessCodeException
import co.paystack.android.model.Card
import co.paystack.android.model.Charge
import com.example.tapshopping.R
import com.example.tapshopping.databinding.FragmentPaymentBinding
import com.example.tapshopping.utillz.isNetworkAvailable
import org.json.JSONException
import java.util.Calendar

class PaymentFragment : Fragment() {
    private var transaction: Transaction? = null
    private var charge: Charge? = null

    private lateinit var binding: FragmentPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        // Inside the PaymentFragment's onCreateView
        val bundle = arguments
        val totalPrice = bundle?.getDouble("totalPrice") ?: 0.0

        binding.btnPlaceOrder.text = getString(R.string.pay_amount, "₦$totalPrice")

        initViews()
        binding.toolbarPaymentFragment.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }

    private fun initViews() {
        addTextWatcherToEditText()

        val totalPrice = arguments?.getDouble("totalPrice", 0.0) ?: 0.0
        binding.btnPlaceOrder.text = getString(R.string.pay_amount, "₦$totalPrice")

        handleClicks()
    }


    private fun addTextWatcherToEditText() {
        binding.btnPlaceOrder.isEnabled = false
        binding.btnPlaceOrder.background = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.btn_round_opaque
        )

        val watcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val s1 = binding.etCardNumber.text.toString()
                val s2 = binding.etExpiryDate.text.toString()
                val s3 = binding.etCvv.text.toString()

                if (s1.isEmpty() || s2.isEmpty() || s3.isEmpty()) {
                    binding.btnPlaceOrder.isEnabled = false
                    binding.btnPlaceOrder.background = ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.btn_round_opaque
                    )
                }

                if (s1.length >= 16 && s2.length == 5 && s3.length == 3) {
                    binding.btnPlaceOrder.isEnabled = true
                    binding.btnPlaceOrder.background = ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.btn_border_blue_bg
                    )
                }

                if (s1.length < 16 || s2.length < 5 || s3.length < 3) {
                    binding.btnPlaceOrder.isEnabled = false
                    binding.btnPlaceOrder.background = ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.btn_round_opaque
                    )
                }

                if (s2.length == 2) {
                    if (start == 2 && before == 1 && !s2.contains("/")) {
                        binding.etExpiryDate.setText(getString(R.string.expiry_space, s2[0]))
                        binding.etExpiryDate.setSelection(1)
                    } else {
                        binding.etExpiryDate.setText(getString(R.string.expiry_slash, s2))
                        binding.etExpiryDate.setSelection(3)
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {}
        }

        binding.etCardNumber.addTextChangedListener(CardNumberTextWatcher())
        binding.etExpiryDate.addTextChangedListener(watcher)
        binding.etCvv.addTextChangedListener(watcher)
    }

    private inner class CardNumberTextWatcher : TextWatcher {
        private var isFormatting: Boolean = false
        private val hyphenInterval = 4

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(editable: Editable?) {
            if (isFormatting) return

            isFormatting = true

            val input = editable?.toString()?.replace("[^\\d]".toRegex(), "") ?: ""

            val sb = StringBuilder()
            for (i in input.indices) {
                if (i > 0 && i % hyphenInterval == 0) {
                    sb.append(" - ")
                }
                sb.append(input[i])
            }

            binding.etCardNumber.removeTextChangedListener(this)
            binding.etCardNumber.setText(sb.toString())
            binding.etCardNumber.setSelection(binding.etCardNumber.text?.length ?: 0)
            binding.etCardNumber.addTextChangedListener(this)

            isFormatting = false
        }
    }

    private fun handleClicks() {
        binding.btnPlaceOrder.setOnClickListener {
            // Ensure this code block is reached by adding a log message
            Log.d("PaymentFragment", "Button clicked") // Check logcat for this message

            if (isNetworkAvailable(requireContext())) {
                binding.loadingPayOrder.visibility = View.VISIBLE
                binding.btnPlaceOrder.visibility = View.GONE
                doPayment()
            } else {
                Toast.makeText(requireContext(), "Please check your internet", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun doPayment() {
        val publicTestKey = "pk_test_c152a8b1d2feca7de3ca2f5fb121be4d759cefb8"
        PaystackSdk.setPublicKey(publicTestKey)

        charge = Charge()
        charge!!.card = loadCardFromForm()
        val totalPrice = arguments?.getDouble("totalPrice", 0.0) ?: 0.0
        charge!!.amount = totalPrice.toInt()

        charge!!.email = "ogunjembolaa@gmail.com"
        charge!!.reference = "payment" + Calendar.getInstance().timeInMillis

        try {
            charge!!.putCustomField("Charged From", "Android SDK")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        doChargeCard()
    }

    private fun loadCardFromForm(): Card {
        val cardNumber = binding.etCardNumber.text.toString().trim()
        val expiryDate = binding.etExpiryDate.text.toString().trim()
        val cvc = binding.etCvv.text.toString().trim()

        val cardNumberWithoutSpace = cardNumber.replace(" ", "")
        val monthValue = expiryDate.substring(0, expiryDate.length.coerceAtMost(2))
        val yearValue = expiryDate.takeLast(2)

        val card: Card = Card.Builder(cardNumberWithoutSpace, 0, 0, "").build()

        card.cvc = cvc

        val sMonth: String = monthValue
        var month = 0
        try {
            month = sMonth.toInt()
        } catch (ignored: Exception) {
        }

        card.expiryMonth = month

        val sYear: String = yearValue
        var year = 0
        try {
            year = sYear.toInt()
        } catch (ignored: Exception) {
        }
        card.expiryYear = year

        return card
    }

    private fun doChargeCard() {
        transaction = null

        PaystackSdk.chargeCard(requireActivity(), charge, object : Paystack.TransactionCallback {
            override fun onSuccess(transaction: Transaction) {
                binding.loadingPayOrder.visibility = View.GONE
                binding.btnPlaceOrder.visibility = View.VISIBLE
                Toast.makeText(requireContext(), "Payment was successful", Toast.LENGTH_LONG).show()
                this@PaymentFragment.transaction = transaction
            }

            override fun beforeValidate(transaction: Transaction) {
                this@PaymentFragment.transaction = transaction
            }

            override fun onError(error: Throwable, transaction: Transaction) {
                binding.loadingPayOrder.visibility = View.GONE
                binding.btnPlaceOrder.visibility = View.VISIBLE

                this@PaymentFragment.transaction = transaction
                if (error is ExpiredAccessCodeException) {
                    this@PaymentFragment.doChargeCard()
                    return
                }

                if (transaction.reference != null) {
                    Toast.makeText(requireContext(), error.message ?: "", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), error.message ?: "", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        binding.loadingPayOrder.visibility = View.GONE
    }
}
