package com.example.tapshopping.ui.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.tapshopping.R
import com.example.tapshopping.data.model.GetUserData
import com.example.tapshopping.databinding.FragmentLoginBinding

import com.example.tapshopping.ui.viewmodel.LoginViewModel


class LoginFragment : Fragment(), View.OnClickListener {
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {

        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                /* R.id.tv_forgot_password -> {
                     findNavController().navigate(R.id.action_loginFragment_to_categoriesFragment)
                 }*/
                R.id.btn_login -> {
                    loginRegisteredUser()
                }

                R.id.tv_register -> {
                    findNavController().navigate(R.id.action_accountFragment_to_registerFragment)
                }
            }
        }
    }

    private fun vallidateLoginDeatails(): Boolean {
        val userName: EditText = binding.tvUsername
        val password: EditText = binding.etPassword

        return when {


            TextUtils.isEmpty(userName.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(context, getString(R.string.error_msg_email), Toast.LENGTH_LONG)
                    .show()
                false
            }

            TextUtils.isEmpty(password.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(context, getString(R.string.error_msg_password), Toast.LENGTH_LONG)
                    .show()

                false
            }

            else -> {

                Toast.makeText(context, "Your details are valid ", Toast.LENGTH_LONG).show()

                true
            }


        }
    }

    private fun observerViewModel() {
        viewModel.users.observe(viewLifecycleOwner) { users ->
            users?.let { result ->
                when {
                    result.isSuccess() -> {
                        Toast.makeText(context, "Successful", Toast.LENGTH_LONG).show()
                    }

                    result.isLoading() -> {
                        binding.loadingBar.visibility = View.GONE
                        binding.listError.visibility = View.GONE
                    }

                    result.isError() -> {
                        binding.listError.visibility = View.GONE
                    }
                }

            }
        }

    }

    private fun loginRegisteredUser() {
        val email_edt: EditText = binding.tvUsername
        val et_password: EditText = binding.etPassword

        if (vallidateLoginDeatails()) {

            val userName = email_edt.text.toString().trim { it <= ' ' }
            val password = et_password.text.toString().trim { it <= ' ' }
            val userData = GetUserData.Data(
                password = password, username = userName
            )
            observerViewModel()
            viewModel.fetchUsers(userData)
        }
    }
}