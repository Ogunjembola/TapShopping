package com.example.tapshopping.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.tapshopping.R
import com.example.tapshopping.data.local.DataStoreManager
import com.example.tapshopping.databinding.FragmentHomeBinding
import com.example.tapshopping.ui.adapter.CategoryAdapter
import com.example.tapshopping.ui.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var productAdapter: ProductAdapter

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
            setUpCatAdapter()
            setUpProductAdapter()
        } else {
            findNavController().navigate(R.id.loginUser)
        }

    }

    private fun setUpCatAdapter() {
        categoryAdapter = CategoryAdapter({}, {})
        binding.homeCategoriesListRv.adapter = categoryAdapter
    }

    private fun setUpProductAdapter() {
        productAdapter = ProductAdapter()
        binding.productRv.adapter = productAdapter
    }

}