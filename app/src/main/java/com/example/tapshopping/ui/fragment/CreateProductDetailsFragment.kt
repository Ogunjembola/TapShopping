package com.example.tapshopping.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tapshopping.R
import com.example.tapshopping.databinding.FragmentCreateProductDetailsBinding

class CreateProductDetailsFragment : Fragment() {
    private lateinit var binding: FragmentCreateProductDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentCreateProductDetailsBinding.inflate(inflater, container, false).run {
            binding = this
            root
        }
    }
}