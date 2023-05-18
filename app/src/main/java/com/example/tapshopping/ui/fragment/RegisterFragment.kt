package com.example.tapshopping.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
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

                Toast.makeText(context, "User Registered in successfully are valid ", Toast.LENGTH_LONG).show()

                true
            }


        }
    }
    private  fun observerViewModel() {
        viewModel.users.observe(viewLifecycleOwner) { users ->
            users?.let { result ->
                when {
                    result.isSuccess() ->{
                       Toast.makeText(context,"Successful",Toast.LENGTH_LONG).show()
                    }
                    result.isLoading() ->{
                        binding.loadingBar.visibility = View.GONE
                        binding.listError.visibility =  View.GONE
                    }
                    result.isError() ->{
                        binding.listError.visibility =  View.GONE
                    }
                }

            }
        }

    }

    private fun registerUser() {
        // Check with validate function if the entries are valid or not.
        if (validateRegistrationDetails()) {

            // Show the progress dialog.
            val et_email: EditText = binding.etEmail
            val et_password: EditText = binding.etPassword
            val et_full_name: EditText = binding.etFullName
            val et_username: EditText = binding.etUsername

            val userData = UserRegistrationData(
                email = et_email.text.toString(),
                name = et_full_name.text.toString(),
                password = et_password.text.toString(),
                username = et_username.text.toString()
            )
            observerViewModel()
            viewModel.registerUser(userData)
        }
    }
}
