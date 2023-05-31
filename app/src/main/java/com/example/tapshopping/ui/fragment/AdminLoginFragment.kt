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
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.tapshopping.R
import com.example.tapshopping.data.local.DataStoreManager
import com.example.tapshopping.databinding.FragmentAdminLoginBinding
import com.example.tapshopping.ui.viewModel.AdminViewModel
import com.example.tapshopping.utillz.SUCCESSFULLY_LOGGED_IN
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AdminLoginFragment : Fragment() {
    lateinit var binding: FragmentAdminLoginBinding
    private val viewModel by viewModels<AdminViewModel>()

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentAdminLoginBinding.inflate(inflater, container, false).run {
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleAdminLogin()
        getAdminData()

        binding.btnLogin.setOnClickListener {
            loginRegisteredUser()
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun validateLoginDetail(): Boolean {
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

    private fun loginRegisteredUser() {
        val userName: EditText = binding.tvUsername
        val password: EditText = binding.etPassword

        if (validateLoginDetail()) {

            val userName = userName.text.toString().trim { it <= ' ' }
            val password = password.text.toString().trim { it <= ' ' }
            viewModel.loginAdminAccount(userName = userName, password = password)
        }
    }

    private fun handleAdminLogin() {
        viewModel.loginAdmin.observe(viewLifecycleOwner) { users ->
            users?.let { result ->
                when {
                    result.isSuccess() -> {
                        viewModel.getAdminData()
                    }

                    result.isLoading() -> {
                        binding.btnLogin.setText(R.string.loading)
                    }

                    result.isError() -> {
                        val errorMessage = result.message
                        binding.btnLogin.setText(R.string.retry)
                        AlertDialog.Builder(requireContext()).setTitle("Failed")
                            .setIcon(R.drawable.baseline_error_24)
                            .setMessage(errorMessage)
                            .setPositiveButton(R.string.retry) { _, _ ->
                                // do nothing
                            }.show()
                        binding.etPassword.setText("")
                        binding.tvUsername.setText("")
                    }
                }

            }
        }

    }

    private fun getAdminData(){
        viewModel.adminData.observe(viewLifecycleOwner) {adminData ->
            adminData?.let {response ->
                when{
                    response.isSuccess() ->{
//                        dataStoreManager.userName = response.data!!.adminData.dataResponse.admin.username
                        Log.d("observer", "observerViewModel: Success")
                        AlertDialog.Builder(requireContext()).setTitle("Successful")
                            .setIcon(R.drawable.successful)
                            .setMessage(SUCCESSFULLY_LOGGED_IN)
                            .setPositiveButton(R.string.proceed){_, _ ->
                            }
                            .show()
                        binding.btnLogin.setText(R.string.login)
                        findNavController().navigate(AdminLoginFragmentDirections.actionAdminLoginToHomeFragment())

//                        findNavController().popBackStack()
                    }
                    response.isError() ->{
                        val errorMessage = response.message
                        binding.btnLogin.setText(R.string.retry)
                        AlertDialog.Builder(requireContext()).setTitle("Failed")
                            .setIcon(R.drawable.baseline_error_24)
                            .setMessage(errorMessage)
                            .setPositiveButton(R.string.retry) { _, _ ->
                                // do nothing
                            }.show()
                    }
                }
            }
        }
    }

    private fun navigateToHome(){
        val startDestination = findNavController()

    }
}