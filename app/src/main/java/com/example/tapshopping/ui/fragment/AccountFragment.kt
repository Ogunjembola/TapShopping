package com.example.tapshopping.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tapshopping.R
import com.example.tapshopping.databinding.FragmentAccountBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class AccountFragment : Fragment(), View.OnClickListener {

    lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return FragmentAccountBinding.inflate(inflater, container, false).run {
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavigationView.visibility = View.VISIBLE
        binding.csAdmin.setOnClickListener (this)
        binding.csProfile.setOnClickListener (this)

    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.cs_admin ->{
                    findNavController().navigate(AccountFragmentDirections.toAdminFragment())
                }
                R.id.cs_profile ->{
                    findNavController().navigate(R.id.action_accountFragment_to_profile2)

                }
            }
        }
    }
}