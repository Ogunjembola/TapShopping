package com.example.tapshopping.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tapshopping.R
import com.example.tapshopping.data.local.DataStoreManager
import com.example.tapshopping.databinding.FragmentAccountBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AccountFragment : Fragment(), View.OnClickListener {

    lateinit var binding: FragmentAccountBinding

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
        binding.csCategory.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.cs_admin -> {
                    findNavController().navigate(AccountFragmentDirections.toCreateAdminFragment())
                }
                R.id.cs_profile -> {
                    findNavController().navigate(R.id.action_accountFragment_to_profile2)
                }
                R.id.cs_category -> {
                    findNavController().navigate(AccountFragmentDirections.toCategoryFragment())
                }
            }
        }
    }

    private fun addProfileDetail() {
        binding.apply {
            val expectedFullName = dataStoreManager.fullName
            fullName.text = expectedFullName
            email.text = dataStoreManager.email
            twType.text = dataStoreManager.userType
            username.text = dataStoreManager.userName

            lifecycleScope.launch {
                dataStoreManager.isAdmin.collect{isAdmin ->
                    csAdmin.isVisible = isAdmin
                }
            }
            val separatedNames: List<String> = expectedFullName.split(" ")
            Log.d("AccountFragment ", "separatedNames: $separatedNames ")
            val shortName:String = separatedNames[0].first() + separatedNames[1].first().toString()
            userProfileTextView.text = shortName
        }
    }
}