package com.example.tapshopping.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tapshopping.R
import com.example.tapshopping.data.local.DataStoreManager
import com.example.tapshopping.data.model.Product
import com.example.tapshopping.databinding.FragmentHomeBinding
import com.example.tapshopping.ui.adapter.CategoryListAdapter
import com.example.tapshopping.ui.adapter.ProductAdapterList
import com.example.tapshopping.ui.viewModel.CategoryViewModel
import com.example.tapshopping.ui.viewModel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val productAdapter = ProductAdapterList { name: Product ->
        clickItem(name)
    }
    private val productViewModel by viewModels<ProductViewModel>()
    private val categoriesViewModel by viewModels<CategoryViewModel>()
    private var categoryAdapter: CategoryListAdapter? = null
    val product: MutableList<Product> = ArrayList()

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (dataStoreManager.userName.isNotEmpty()) {
            //  setUpCatAdapter()
            setUpAdapter()
            productViewModel.fetchProducts()
            categoriesViewModel.getCategories()
            productObserverViewModel()
        } else {
            findNavController().navigate(R.id.loginUser)
        }
    }
    private fun setUpAdapter() {
        //Initialize the productAdapter
        val recyclerView: RecyclerView = binding.productRv
        recyclerView.apply {
            layoutManager = GridLayoutManager(activity, 2)
            setHasFixedSize(true)
            adapter = productAdapter
        }

        categoryAdapter = CategoryListAdapter() // Initialize categoryAdapter here

        val RV: RecyclerView = binding.homeCategoriesListRv
        RV.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
    }

    private fun productObserverViewModel() {
        productViewModel.products.observe(viewLifecycleOwner) { data ->
            data?.let { result ->
                when {
                    result.isSuccess() -> {
                        binding.productRv.visibility = View.VISIBLE
                        val productList = result.data!!.content.products
                        productAdapter.updateProduct(productList)
                    }

                    result.isLoading() -> {
                        binding.loadingBar.isVisible = true
                    }

                    result.isError() -> {
                        binding.loadingBar.isVisible = false
                        binding.listError.visibility = View.VISIBLE
                        val errorMessage = result.message ?: "Unknown error"
                        Log.e(
                            "data error",
                            "Failed to fetch products: $errorMessage"
                        )
                    }
                }
            }
        }
        categoriesViewModel.getCategories.observe(viewLifecycleOwner){ response ->
            response?.let { result ->
                when{
                    result.isSuccess() ->{
                        val categoryList = result.data!!.categoryContent.categories
                        categoryAdapter?.updateCategory(categoryList)
                        Log.d("data success", "productObserverViewModel: $categoryList ")
                    }
                }
                binding.loadingBar.isVisible = result.isLoading()
                binding.listError.isVisible = result.isError()
                binding.listError.text = result.message
            }
        }
    }

    private fun clickItem(data: Product) {
        val bundle = Bundle()
        bundle.putSerializable("value", data)
        findNavController().navigate(R.id.productDetails, bundle)
    }
}

