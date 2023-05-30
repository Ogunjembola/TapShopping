package com.example.tapshopping.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.tapshopping.R
import com.example.tapshopping.databinding.FragmentAdminBinding

class AdminFragment : Fragment() {

    lateinit var binding: FragmentAdminBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentAdminBinding.inflate(inflater, container, false).run {
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            toolbarAdminAccount.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            cardAdmin.setOnClickListener {
                findNavController().navigate(AdminFragmentDirections.toCreateAdminFragment())
            }
        }
    }

}