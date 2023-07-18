package com.example.tapshopping.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageData = CreateProductDetailsFragmentArgs.fromBundle(requireArguments()).imageData
        Log.d(
            "CreateProductDetailsFragment",
            "Category Id: ${imageData[0]} \n First Image: ${imageData[1]}  \n Second image: ${imageData[2]} \n" +
                    " Third image: ${imageData[3]} \n" +
                    " Forth image: ${imageData[4]}  "
        )

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}