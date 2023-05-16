package com.example.tapshopping.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tapshopping.R
import com.example.tapshopping.data.model.DataModel
import com.example.tapshopping.data.model.UserRegistrationData
import com.example.tapshopping.databinding.FragmentRegisterBinding
import com.example.tapshopping.ui.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private val viewModel: RegisterViewModel by viewModels()
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
        }

    }

    private fun validateRegistrationDetails(): Boolean {
        val fullName: EditText = binding.etFullName
        val username: EditText = binding.etUserName
        val email: EditText = binding.etEmail
        val password: EditText = binding.etPassword
        val confirmPassword: EditText = binding.etConfirmPassword
        val cb_terms_and_condition: CheckBox = binding.cbTermsAndCondition
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

            !cb_terms_and_condition.isChecked -> {
                Toast.makeText(
                    context, getString(R.string.error_msg_agree_terms), Toast.LENGTH_LONG
                ).show()
                false
            }

            else -> {

                Toast.makeText(context, "Your details are valid ", Toast.LENGTH_LONG).show()

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
            val et_username: EditText = binding.etUserName

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