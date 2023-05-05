package com.example.tapshopping.data.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tapshopping.data.model.Category
import com.example.tapshopping.data.model.categories
import com.example.tapshopping.data.model.products
import com.example.tapshopping.data.view.adapter.CategoryAdapter
import com.example.tapshopping.data.view.adapter.ProductAdapter
import com.example.tapshopping.databinding.FragmentProductBinding

class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var productAdapter: ProductAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpCatAdapter()
        setUpProductAdapter()
    }

    private fun setUpCatAdapter(){
        categoryAdapter = CategoryAdapter(categories = categories )
        binding.homeCategoriesListRv.adapter = categoryAdapter
    }

    private fun setUpProductAdapter(){
        productAdapter = ProductAdapter(products = products )
        binding.productRv.adapter = productAdapter
    }

}