package com.example.tapshopping.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tapshopping.R
import com.example.tapshopping.data.local.DataStoreManager
import com.example.tapshopping.databinding.FragmentUserLoginBinding
import com.example.tapshopping.ui.viewModel.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserLoginFragment : Fragment(), View.OnClickListener {
    // private val viewModel:  by viewModels()
    @Inject
    lateinit var dataStoreManager: DataStoreManager

    private val viewModel by viewModels<UserViewModel>()
    private lateinit var binding: FragmentUserLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerViewModel()
        // Click event assigned to Forgot Password text.
        binding.tvForgotPassword.setOnClickListener(this)
        // Click event assigned to Login button.
        binding.btnLogin.setOnClickListener(this)

        // Click event assigned to Register text.
        binding.tvCustomer.setOnClickListener(this)
        binding.tvMerchantLogin.setOnClickListener(this)
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavigationView.visibility = View.GONE

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


                R.id.tv_customer -> {
                    findNavController().navigate(R.id.action_loginUser_to_registerUserFragment)

                }

                R.id.tv_merchant_login -> {
                    findNavController().navigate(R.id.action_loginUser_to_adminLogin)

                }
            }
        }
    }

    private fun vallidateLoginDeatails(): Boolean {
        val userName: EditText = binding.tvUsername
        val password: EditText = binding.etPassword

        return when {


            TextUtils.isEmpty(userName.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(context, getString(R.string.error_msg_username), Toast.LENGTH_LONG)
                    .show()
                false
            }

            TextUtils.isEmpty(password.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(context, getString(R.string.error_msg_password), Toast.LENGTH_LONG)
                    .show()

                false
            }

            else -> {


                true
            }


        }
    }

    private fun observerViewModel() {
        viewModel.userLogin.observe(viewLifecycleOwner) { users ->
            users?.let { result ->
                when {
                    result.isSuccess() -> {
                        clearTextField()
//                        Log.d("observer", "observerViewModel: Success")
//                        AlertDialog.Builder(requireContext()).setTitle("Successful")
//                            .setIcon(R.drawable.successful)
//                            .setMessage(result.data!!.success.message)
//                            .setPositiveButton("Proceed") { _, _ ->
////                                findNavController().navigateUp()
//                            }.show()
                        dataStoreManager.setIsLoggedIn(true)
                        findNavController().popBackStack()
                        binding.btnLogin.setText(R.string.login)
                    }

                    result.isLoading() -> {
                        binding.btnLogin.setText(R.string.loading)
                    }

                    result.isError() -> {


                        val errorMessage = result.message
                        Log.d("errorobsevrer", errorMessage)
                        binding.btnLogin.setText(R.string.retry)
                        AlertDialog.Builder(requireContext()).setTitle("Failed")
                            .setIcon(R.drawable.baseline_error_24)
                            .setMessage(errorMessage)
                            .setPositiveButton(R.string.retry) { _, _ ->
                                // do nothing
                            }.show()
                        //clearTextField()
                    }
                }

            }
        }

    }

    private fun loginRegisteredUser() {
        val _userName: EditText = binding.tvUsername
        val et_password: EditText = binding.etPassword

        if (vallidateLoginDeatails()) {

            val userName = _userName.text.toString().trim { it <= ' ' }
            val password = et_password.text.toString().trim { it <= ' ' }
            viewModel.fetchUsers(userName = userName, password = password)
        }
    }

    private fun clearTextField() {
        binding.tvUsername.setText("")
        binding.etPassword.setText("")
    }
}