package com.example.tapshopping.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tapshopping.R
import com.example.tapshopping.databinding.FragmentUpdateAdminBinding
import com.example.tapshopping.ui.viewModel.AdminViewModel
import com.example.tapshopping.utillz.INFO_UPDATE_SUCCESSFUL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateAdminFragment : Fragment() {
    lateinit var binding: FragmentUpdateAdminBinding
    private val viewModel by viewModels<AdminViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentUpdateAdminBinding.inflate(inflater, container, false).run {
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleAdminUpdate()
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            btnUpdateAdmin.setOnClickListener {
                activateAdminUpdate()
            }
        }
    }


    private fun validateInputData(): Boolean {
        val userName: EditText = binding.tvUsername
        val password: EditText = binding.etPassword
        val fullName: EditText = binding.tvFullName

        return when {
            TextUtils.isEmpty(fullName.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(context, getString(R.string.error_msg_full_name), Toast.LENGTH_LONG)
                    .show()

                false
            }
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

    private fun activateAdminUpdate() {
        if (validateInputData()) {
            val userName = binding.tvUsername.text.toString().trim { it <= ' ' }
            val password = binding.etPassword.text.toString().trim { it <= ' ' }
            val fullName = binding.tvFullName.text.toString().trim { it <= ' ' }
            viewModel.updateAdminData(fullName = fullName, password = password, userName = userName)
        }
    }

    private fun handleAdminUpdate() {
        viewModel.updateAdmin.observe(viewLifecycleOwner) { users ->
            users?.let { result ->
                binding.loadingBar.isVisible = result.isLoading()
                when {
                    result.isSuccess() -> {
                        AlertDialog.Builder(requireContext()).setTitle("Successful")
                            .setIcon(R.drawable.successful)
                            .setMessage(INFO_UPDATE_SUCCESSFUL)
                            .setPositiveButton(R.string.proceed) { _, _ ->
                                findNavController().navigateUp()
                            }
                            .show()
                    }

                    result.isLoading() -> {
                        binding.btnUpdateAdmin.setText(R.string.loading)
                        binding.btnUpdateAdmin.isEnabled = false
                    }

                    result.isError() -> {
                        val errorMessage = result.message
                        binding.btnUpdateAdmin.setText(R.string.retry)
                        AlertDialog.Builder(requireContext()).setTitle("Failed")
                            .setIcon(R.drawable.baseline_error_24)
                            .setMessage(errorMessage)
                            .setPositiveButton(R.string.retry) { _, _ ->
                                // do nothing
                            }.show()
                        binding.etPassword.setText("")
                        binding.tvUsername.setText("")
                        binding.tvFullName.setText("")
                    }
                }

            }
        }

    }

}