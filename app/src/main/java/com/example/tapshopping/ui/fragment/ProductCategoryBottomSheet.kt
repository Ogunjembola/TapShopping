package com.example.tapshopping.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tapshopping.databinding.ProductCategoryBinding
import com.example.tapshopping.ui.adapter.ProductCategoryAdapter
import com.example.tapshopping.ui.viewModel.CategoryViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductCategoryBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: ProductCategoryBinding
    private lateinit var adapter: ProductCategoryAdapter
    private val viewModel by viewModels<CategoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ProductCategoryBinding.inflate(inflater, container, false).run {
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ProductCategoryAdapter{
            findNavController().navigate(AccountFragmentDirections.toCreateProductFragment(it.categoryId))
            dismiss()
//            Toast.makeText(context, "${it.categoryName} is selected", Toast.LENGTH_SHORT ).show()
        }

        binding.recyclerView.adapter = adapter

        viewModel.getCategories()
        handleGetCategoriesObserver()
    }

    private fun handleGetCategoriesObserver() {
        viewModel.getCategories.observe(viewLifecycleOwner) { response ->
            response?.let { result ->

                when {
                    result.isSuccess() -> {
                        adapter.categories = result.data!!.categoryContent.categories
                        adapter.notifyDataSetChanged()
                    }
                }
                binding.productCat.isVisible = result.isSuccess()
                binding.progressBar.isVisible = result.isLoading()
                binding.errorMessage.isVisible = result.isError()
                binding.errorMessage.text = result.message
            }

        }
    }
}