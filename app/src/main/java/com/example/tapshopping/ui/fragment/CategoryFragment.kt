package com.example.tapshopping.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tapshopping.R
import com.example.tapshopping.databinding.FragmentCategoryBinding
import com.example.tapshopping.ui.adapter.CategoryAdapter
import com.example.tapshopping.ui.viewModel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

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

        categoryAdapter = CategoryAdapter({ index ->
            position = index
        }, {
            showHideMenu(it)
        })
        viewModel.getCategories()
        handleGetCategoriesObserver()
        handleOptionMenu()

        binding.apply {
            fab.setOnClickListener {
                findNavController().navigate(CategoryFragmentDirections.actionCategoryFragmentToCreateCategoryFragment())
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
//            toolbarCategory.setOnMenuItemClickListener {menuItem ->
//                when(menuItem.itemId){
//                }
//            }

        }
    }

    private fun showHideMenu(show: Boolean) {
        binding.toolbarCategory.apply {
            menu.findItem(R.id.delete_cat).isVisible = show
            menu.findItem(R.id.edit_cat).isVisible = show
            menu.findItem(R.id.cancel_action).isVisible = show
        }
    }

}