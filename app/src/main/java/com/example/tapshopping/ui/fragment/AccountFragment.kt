package com.example.tapshopping.ui.fragment

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tapshopping.R
import com.example.tapshopping.data.local.DataStoreManager
import com.example.tapshopping.databinding.FragmentAccountBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class AccountFragment : Fragment(), View.OnClickListener {

    lateinit var binding: FragmentAccountBinding
    lateinit var profileTextView: TextView

    @Inject
    lateinit var dataStoreManager: DataStoreManager

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

        addProfileDetail()

        binding.csAdmin.setOnClickListener(this)
        binding.csProfile.setOnClickListener(this)

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavigationView.visibility = View.VISIBLE
        profileTextView = binding.userProfileTextView
        val firstName = DataStoreManager.FULL_NAME
        val lastName = DataStoreManager.EMAIL
        val shortName = firstName.first().toString() + lastName.first().toString()
        profileTextView .text = shortName

        binding.fullName.text= DataStoreManager.FULL_NAME
        binding.email.text= DataStoreManager.EMAIL


        binding.csAdmin.setOnClickListener (this)
        binding.csProfile.setOnClickListener (this)

    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.cs_admin -> {
                    findNavController().navigate(AccountFragmentDirections.toAdminFragment())
                }
                R.id.cs_profile -> {
                    findNavController().navigate(R.id.action_accountFragment_to_profile2)

                }
            }
        }
    }

    private fun addProfileDetail() {
        binding.apply {
            fullName.text = dataStoreManager.fullName
            email.text = dataStoreManager.email
            twType.text = dataStoreManager.userType
            username.text = dataStoreManager.userName

            lifecycleScope.launch {
                dataStoreManager.isAdmin.collect{isAdmin ->
                    csAdmin.isVisible = isAdmin
                }
            }

        }
    }
}