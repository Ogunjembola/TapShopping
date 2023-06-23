package com.example.tapshopping.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tapshopping.R
import com.example.tapshopping.data.model.CategoryData
import com.example.tapshopping.databinding.FragmentCategoryBinding
import com.example.tapshopping.ui.adapter.CategoryAdapter
import com.example.tapshopping.ui.viewModel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "CategoryFragment"

@AndroidEntryPoint
class CategoryFragment : Fragment() {
    lateinit var binding: FragmentCategoryBinding
    private val viewModel by viewModels<CategoryViewModel>()
    private lateinit var categoryAdapter: CategoryAdapter
    var position = -1
    var show = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentCategoryBinding.inflate(inflater, container, false).run {
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryAdapter = CategoryAdapter({ category ->
            deleteCategory(category)
            Log.d(TAG, "onViewCreated: categoryId = ${category.categoryId} ")
        }, {
            showHideMenu(it)
        })
        viewModel.getCategories()
        handleGetCategoriesObserver()
        handleOptionMenu()
        handleDeleteCategoryObserver()

        binding.apply {
            fab.setOnClickListener {
                findNavController().navigate(CategoryFragmentDirections.toCreateCategoryFragment(null))
            }
            toolbarCategory.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            recyclerView.adapter = categoryAdapter
        }
    }

    private fun handleGetCategoriesObserver() {
        viewModel.getCategories.observe(viewLifecycleOwner) { response ->
            response?.let { result ->

                when {
                    result.isSuccess() -> {
                        categoryAdapter.categories = result.data!!.categoryContent.categories
                        categoryAdapter.notifyDataSetChanged()
                    }
                }
                binding.progressBar.isVisible = result.isLoading()
                binding.errorMessage.isVisible = result.isError()
                binding.errorMessage.text = result.message
            }

        }
    }

    private fun handleOptionMenu() {
        binding.apply {
            toolbarCategory.inflateMenu(R.menu.category_menu)
            showHideMenu(false)
        }
    }

    private fun showHideMenu(show: Boolean) {
        binding.toolbarCategory.apply {
            menu.findItem(R.id.delete_cat).isVisible = show
            menu.findItem(R.id.edit_cat).isVisible = show
            menu.findItem(R.id.cancel_action).isVisible = show
        }
    }

    private fun deleteCategory(category: CategoryData) {
        binding.toolbarCategory.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.delete_cat -> {
                    viewModel.deleteCategory(categoryId = category.categoryId)
                    true
                }
                R.id.edit_cat -> {
                    findNavController().navigate(CategoryFragmentDirections.toCreateCategoryFragment(category))
                    true
                }

                else -> false
            }
        }
    }

    private fun handleDeleteCategoryObserver() {
        viewModel.deleteCategory.observe(viewLifecycleOwner) { response ->
            response?.let { result ->
                when{
                    result.isSuccess() -> {
                        AlertDialog.Builder(requireContext()).setTitle("Successful")
                            .setIcon(R.drawable.successful)
                            .setMessage(result.data!!.success.message)
                            .setPositiveButton("Proceed") { _, _ ->
                                findNavController().navigateUp()
                            }.show()
                    }
                    result.isError() -> {
                        val errorMessage = result.message
                        AlertDialog.Builder(requireContext()).setTitle("Failed")
                            .setIcon(R.drawable.baseline_error_24)
                            .setMessage(errorMessage)
                            .setPositiveButton(R.string.retry) { _, _ ->
                                // do nothing
                            }.show()
                    }
                }
                binding.progressBar.isVisible = result.isLoading()

            }
        }
    }

}