package com.example.tapshopping.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
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

            cardAdmin.setOnClickListener {
                findNavController().navigate(AdminFragmentDirections.actionAdminFragmentToCreateAdminFragment())
            }
        }
    }

}