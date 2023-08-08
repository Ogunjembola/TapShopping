package com.example.tapshopping.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

import com.example.tapshopping.databinding.FragmentPaystackBinding

import com.example.tapshopping.utillz.isNetworkAvailable
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import java.util.Calendar

@AndroidEntryPoint
class PaystackFragment : Fragment() {
    private lateinit var binding: FragmentPaystackBinding
    private var transaction: Transaction? = null
    private var charge: Charge? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaystackBinding.inflate(inflater, container, false)
        handleClicks()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val etExpiryDate = binding.etExpiryDate
        etExpiryDate.addTextChangedListener(ExpiryDateTextWatcher())
        val etCardNumber = binding.etCardNumber
        etCardNumber.addTextChangedListener(CardNumberTextWatcher())
        binding.toolbarPaymentFragment.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private inner class ExpiryDateTextWatcher : TextWatcher {
        private var isFormatting: Boolean = false
        private val slashPosition = 2

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            binding.btnPlaceOrder.isEnabled = false
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val s1 = binding.etCardNumber.text.toString()
            val s2 = binding.etExpiryDate.text.toString()
            val s3 = binding.etCvv.text.toString()

            //check if they are empty, make button unclickable
            if (s1.isEmpty() || s2.isEmpty() || s3.isEmpty()) {
                binding.btnPlaceOrder.isEnabled = false
            }
            //check the length of all edit text, if meet the required length, make button clickable
            if (s1.length >= 16 && s2.length == 5 && s3.length == 3) {
                binding.btnPlaceOrder.isEnabled = true

            }
            //if edit text doesn't meet the required length, make button unclickable. You could use else statement from the above if
            if (s1.length < 16 || s2.length < 5 || s3.length < 3) {
                binding.btnPlaceOrder.isEnabled = false

            }
        }

        override fun afterTextChanged(editable: Editable?) {
            if (isFormatting) return

            isFormatting = true

            // Remove all non-numeric characters
            val input = editable?.toString()?.replace("[^\\d]".toRegex(), "") ?: ""

            val sb = StringBuilder()
            for (i in input.indices) {
                if (i == slashPosition && i < input.length) {
                    sb.append(" / ")
                }
                sb.append(input[i])
            }

            binding.etExpiryDate.setText(sb.toString())
            binding.etExpiryDate.setSelection(binding.etExpiryDate.text?.length ?: 0)

            isFormatting = false
        }

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
            if (isNetworkAvailable(requireContext())) {
                binding.loadingPayOrder.visibility = View.VISIBLE
                binding.btnPlaceOrder.visibility = View.GONE

            } else {
                Toast.makeText(requireContext(), "Please check your internet", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }



    override fun onPause() {
        super.onPause()
        binding.loadingPayOrder.visibility = View.GONE
    }


}
