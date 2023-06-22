package com.example.tapshopping.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tapshopping.R
import com.example.tapshopping.databinding.FragmentCreateAdminBinding
import com.example.tapshopping.databinding.FragmentCreateCategoryBinding
import com.example.tapshopping.ui.viewModel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCategoryFragment : Fragment() {
    private lateinit var binding:FragmentCreateCategoryBinding
    private val viewModel by viewModels<CategoryViewModel> ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentCreateCategoryBinding.inflate(inflater, container, false).run {
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleCreateCategory()

        binding.btnCreateCategory.setOnClickListener{
            createCategory()
        }
    }

    private fun handleEditText(): Boolean{

        return when {
            TextUtils.isEmpty(binding.etCategoryName.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(context, getString(R.string.error_msg_category_name), Toast.LENGTH_LONG)
                    .show()
                false
            }
            TextUtils.isEmpty(binding.etDescription.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(context, getString(R.string.error_msg_cat_description), Toast.LENGTH_LONG)
                    .show()

                false
            }

            else -> {
                true
            }
        }
    }
    private fun createCategory() {
        if (handleEditText()) {
            val categoryName = binding.etCategoryName.text.toString().trim { it <= ' ' }
            val categoryDescription = binding.etDescription.text.toString().trim { it <= ' ' }
            viewModel.createCategory(categoryName, categoryDescription)
        }
    }
    private fun handleCreateCategory(){
        viewModel.createCategory.observe(viewLifecycleOwner){response ->
            response?.let{result ->
                binding.progressBar.isVisible = result.isLoading()
                when{
                    result.isSuccess()->{

                        AlertDialog.Builder(requireContext()).setTitle("Successful")
                            .setIcon(R.drawable.successful)
                            .setMessage(result.data!!.success.message)
                            .setPositiveButton("Proceed") { _, _ ->
                                findNavController().navigateUp()
                            }.show()
                        binding.btnCreateCategory.setText(R.string.create_category)

                    }
                    result.isLoading() ->{
                        binding.btnCreateCategory.setText(R.string.loading)
                        binding.btnCreateCategory.isEnabled = true
                    }
                    result.isError() ->{

                        val errorMessage = result.message
                        binding.btnCreateCategory.setText(R.string.retry)
                        AlertDialog.Builder(requireContext()).setTitle("Failed")
                            .setIcon(R.drawable.baseline_error_24)
                            .setMessage(errorMessage)
                            .setPositiveButton(R.string.retry) { _, _ ->
                                // do nothing
                            }.show()
                        binding.etCategoryName.setText("")
                        binding.etDescription.setText("")
                    }
                }

            }

        }
    }
}