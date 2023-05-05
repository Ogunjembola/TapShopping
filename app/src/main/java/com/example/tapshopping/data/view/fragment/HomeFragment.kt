package com.example.tapshopping.data.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.tapshopping.R
import com.example.tapshopping.data.model.Product
import com.example.tapshopping.data.view.adapter.CategoryAdapter
import com.example.tapshopping.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var categoriesList : ArrayList<Product>
    private lateinit var homeAdapter : CategoryAdapter
    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBottomNav()
    }

    private fun setUpBottomNav(){
        val navHostFragment = childFragmentManager.findFragmentById(R.id.homeFragment) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navController = navHostFragment.navController)
    }



}