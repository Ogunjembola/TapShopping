package com.example.tapshopping.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tapshopping.databinding.FragmentHomeBinding
import com.example.tapshopping.ui.adapter.CategoryAdapter
import com.example.tapshopping.ui.adapter.ProductAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var productAdapter: ProductAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpCatAdapter()
        setUpProductAdapter()
    }

    private fun setUpCatAdapter(){
        categoryAdapter = CategoryAdapter()
        binding.homeCategoriesListRv.adapter = categoryAdapter
    }

    private fun setUpProductAdapter(){
        productAdapter = ProductAdapter()
        binding.productRv.adapter = productAdapter
    }

}