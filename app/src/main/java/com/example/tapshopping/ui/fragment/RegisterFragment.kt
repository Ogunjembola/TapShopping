package com.example.tapshopping.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tapshopping.R
import com.example.tapshopping.data.model.UserRegistrationData
import com.example.tapshopping.databinding.FragmentRegisterBinding
import com.example.tapshopping.ui.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    //private val viewModel: LoginViewModel by viewModels()
    private val viewModel by viewModels<UserViewModel>()
    private lateinit var binding: FragmentRegisterBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            //viewModel.postUsers()
            registerUser()
            //activity?.finish()
        }
        binding.toolbarRegisterActivity.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun validateRegistrationDetails(): Boolean {
        val fullName: EditText = binding.etFullName
        val username: EditText = binding.etUsername
        val email: EditText = binding.etEmail
        val password: EditText = binding.etPassword
        val confirmPassword: EditText = binding.etConfirmPassword
        return when {
            TextUtils.isEmpty(fullName.text.toString().trim() { it <= ' ' }) -> {
                Toast.makeText(context, getString(R.string.error_msg_full_name), Toast.LENGTH_LONG)
                    .show()
                false
            }

            TextUtils.isEmpty(username.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(context, getString(R.string.error_msg_username), Toast.LENGTH_LONG)
                    .show()
                false
            }

            TextUtils.isEmpty(email.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(context, getString(R.string.error_msg_email), Toast.LENGTH_LONG)
                    .show()
                false
            }

            TextUtils.isEmpty(password.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(context, getString(R.string.error_msg_password), Toast.LENGTH_LONG)
                    .show()

                false
            }

            TextUtils.isEmpty(confirmPassword.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    context, getString(R.string.error_msg_confirm_password), Toast.LENGTH_LONG
                ).show()
                false
            }

            password.text.toString().trim { it <= ' ' } != confirmPassword.text.toString()
                .trim { it <= ' ' } -> {


                Toast.makeText(
                    context,
                    getString(R.string.error_msg_password_and_confirm_password_mismatch),
                    Toast.LENGTH_LONG
                ).show()
                false
            }


            else -> {


                true
            }


        }
    }

    private fun observerViewModel() {
        viewModel.users.observe(viewLifecycleOwner) { users ->
            users?.let { result ->
                binding.loadingBar.isVisible = result.isLoading()
                binding.btnRegister.isEnabled = !result.isLoading()

                when {
                    result.isSuccess() -> {
                        clearTextField()

                        AlertDialog.Builder(requireContext()).setTitle("Successful")
                            .setIcon(R.drawable.successful)
                            .setMessage(result.data!!.success.message)
                            .setPositiveButton("Proceed") { _, _ ->
                                findNavController().navigateUp()
                            }.show()
                        binding.btnRegister.setText(R.string.create_user)
                    }

                    result.isLoading() -> {
                        binding.btnRegister.setText(R.string.loading)
                    }

                    result.isError() -> {
                        val errorMessage = result.message
                        binding.btnRegister.setText(R.string.retry)
                        AlertDialog.Builder(requireContext()).setTitle("Failed")
                            .setIcon(R.drawable.baseline_error_24)
                            .setMessage(errorMessage)
                            .setPositiveButton(R.string.retry) { _, _ ->
                                // do nothing
                            }.show()
                        clearTextField()
                    }
                }

            }
        }

    }

    private fun registerUser() {
        // Check with validate function if the entries are valid or not.
        if (validateRegistrationDetails()) {

            // Show the progress dialog.
            val et_email: TextView = binding.etEmail
            val et_password: EditText = binding.etPassword
            val et_full_name: EditText = binding.etFullName
            val et_username: EditText = binding.etUsername
            val email: String = et_email.text.toString().trim { it <= ' ' }
            val username: String = et_username.text.toString().trim { it <= ' ' }
            val full_name: String = et_full_name.text.toString().trim { it <= ' ' }
            val password: String = et_password.text.toString().trim { it <= ' ' }
            viewModel.registerUser(
                fullName = full_name,
                userName = username,
                email = email,
                password = password
            )

            observerViewModel()

        } else Toast.makeText(requireContext(), "Text input not completed", Toast.LENGTH_SHORT)
            .show()
    }

    private fun clearTextField() {
        binding.etEmail.setText("")
        binding.etUsername.setText("")
        binding.etPassword.setText("")
        binding.etFullName.setText("")
        binding.etConfirmPassword.setText("")
        binding.tilUsername.helperText = "*Required"
        binding.tilEmail.helperText = "*Required"
        binding.tilPassword.helperText = "*Required"
        binding.tilFullName.helperText = "*Required"
        binding.tilConfirmPassword.helperText = "*Required"
    }
}
