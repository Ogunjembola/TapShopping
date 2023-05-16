package com.example.tapshopping.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.example.tapshopping.R
import com.example.tapshopping.databinding.FragmentCreateAdminBinding
import com.example.tapshopping.ui.viewModel.AdminViewModel
import com.example.tapshopping.utillz.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateAdminFragment : Fragment() {
    lateinit var binding: FragmentCreateAdminBinding
    private var emailText: String? = null
    private var username: String? = null
    private var fullName: String? = null
    private var password: String? = null
    private var confirmPassword: String? = null
    private val viewModel by viewModels<AdminViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentCreateAdminBinding.inflate(inflater, container, false).run {
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textInputValidator()

        binding.btnRegister.setOnClickListener {
            if (isEnabled()) {
                viewModel.createAdminAccount(
                    fullName = fullName!!,
                    userName = username!!,
                    email = emailText!!,
                    password = password!!
                )
            } else Toast.makeText(requireContext(), "Text input not completed", Toast.LENGTH_SHORT)
                .show()
        }

        binding.toolbarRegisterActivity.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        //This method houses the viewModel observer and the request logic
        processRequest()

    }

    private fun processRequest(){
        viewModel.createAdmin.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    binding.btnRegister.setText(R.string.loading)
                }
                is Resource.Success -> {
                    binding.etEmail.setText("")
                    binding.etUsername.setText("")
                    binding.etPassword.setText("")
                    binding.etFullName.setText("")
                    binding.etConfirmPassword.setText("")

                    AlertDialog.Builder(requireContext()).setTitle("Successful")
                        .setIcon(R.drawable.successful)
                        .setMessage(response.data!!.success.message)
                        .setPositiveButton("Proceed"){_,_ ->
                            findNavController().navigateUp()
                        }.show()
                }
                else -> {
                    val errorMessage = response.data?.errorResponse?.message
                    val unknownError = response.error.toString()
                    Log.d(
                        "CreateAdminFragment",
                        "onViewCreated:$unknownError  -> $errorMessage "
                    )
                    binding.btnRegister.setText(R.string.retry)
                    AlertDialog.Builder(requireContext()).setTitle("Failed")
                        .setIcon(R.drawable.baseline_error_24)
                        .setMessage(errorMessage?:unknownError)
                        .setPositiveButton(R.string.retry){_,_ ->
                            // do nothing
                        }.show()
                }
            }
        }
    }

    private fun textInputValidator() {
        binding.etEmail.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.tilEmail.helperText = validEmail()
            }
        }
        binding.etPassword.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.tilPassword.helperText = validPassword()
            }
        }
        binding.etConfirmPassword.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.tilConfirmPassword.helperText = validConfirmPassword()
            }
        }
        binding.etUsername.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.tilUsername.helperText = validUserName()
            }
        }
        binding.etFullName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.tilFullName.helperText = validFullName()
            }
        }
    }

    private fun validConfirmPassword(): String? {
        confirmPassword = binding.etConfirmPassword.text.toString()
        if (confirmPassword != password) {
            return "Password does not match"
        }
        return null
    }

    private fun validEmail(): String? {
        emailText = binding.etEmail.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Invalid Email Address"
        }
        return null
    }

    private fun validFullName(): String? {
        fullName = binding.etFullName.text.toString()
        if (!fullName!!.matches("^[A-Za-z]+ [A-Za-z]+\$".toRegex())) {
            return "Invalid Full Name"
        } else if (fullName.isNullOrEmpty()) {
            return "Field cannot be empty"
        }
        return null
    }

    private fun validUserName(): String? {
        username = binding.etUsername.text.toString()
        if (username!!.length < 5) {
            return "Minimum 5 character username"
        }
        return null
    }

    private fun validPassword(): String? {
        password = binding.etPassword.text.toString()
        if (password!!.length < 8) {
            return "Minimum 8 character password"
        }
        if (!password!!.matches(".*[A-Z].*".toRegex())) {
            return "Must contain 1 uppercase character"
        }
        if (password!!.matches("^[a-z]+$".toRegex())) {
            return "Must contain 1 lowercase character"
        }
        if (password!!.matches("^[@#\$%^&+=]+$".toRegex())) {
            return "Must contain 1 special character (@#\$%^&+=)"
        }
        return null
    }

    private fun isEnabled(): Boolean {
        val validName = binding.tilFullName.helperText == null
        val validUserName = binding.tilUsername.helperText == null
        val validPassword = binding.tilPassword.helperText == null
        val validEmail = binding.tilEmail.helperText == null
        val validConfirmPassword = binding.tilConfirmPassword.helperText == null
        return validName && validUserName && validPassword && validEmail && validConfirmPassword
    }

}