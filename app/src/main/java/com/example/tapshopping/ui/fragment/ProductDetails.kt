package com.example.tapshopping.ui.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.tapshopping.R
import com.example.tapshopping.data.local.DataStoreManager
import com.example.tapshopping.data.model.Product
import com.example.tapshopping.data.model.Variant
import com.example.tapshopping.databinding.FragmentProductDetailsBinding
import com.example.tapshopping.ui.viewModel.AdminViewModel
import com.example.tapshopping.ui.viewModel.CartViewModel
import com.example.tapshopping.utillz.GlideLoader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetails : Fragment(), View.OnClickListener {
    private var mProductDetails : Product? = null
    private lateinit var binding: FragmentProductDetailsBinding
    private val viewModel by viewModels<CartViewModel>()
    @Inject
    lateinit var dataStoreManager: DataStoreManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        
        // Inflate the layout for this fragment
        binding = FragmentProductDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mProductDetails= arguments?.getSerializable("value") as Product
        mProductDetails?.productId
            // Log.d("ProductDetailsFragment", "Product ID: ${mProductDetails?.productId}")
        mProductDetails = Product(
            __v = mProductDetails?.__v ?: 0,
            averageRating = mProductDetails?.averageRating ?: 0,
            categoryID = mProductDetails?.categoryID ?: "",
            createdAt = mProductDetails?.createdAt ?: "",
            description =mProductDetails?.description ?: "" ,
            discount =mProductDetails?.discount ?: 0 ,
            images = mProductDetails?.images ?: listOf(),
            inStock = mProductDetails?.inStock ?: false,
            name =mProductDetails?.name ?: "" ,
            noOfRatings = mProductDetails?.noOfRatings ?:0,
            noOfReviews =mProductDetails?.noOfReviews ?:0,
            price = mProductDetails?.price ?:0,
            productId = mProductDetails?.productId ?: "" ,
            quantity = mProductDetails?.quantity ?: 0,
            ratings = mProductDetails?.ratings ?: listOf(),
            reviews = mProductDetails?.reviews ?: listOf(),
            updatedAt = mProductDetails?.updatedAt ?: "",
            variant = mProductDetails?.variant ?: listOf()
        )

         productDetailsSuccess(mProductDetails!!)
        observerViewModel()
       binding.btnAddToCart.setOnClickListener(this)
        binding.btnGoToCart.setOnClickListener(this)
        binding.toolbarProductDetailsActivity.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn_go_to_cart -> {
                    findNavController().navigate(R.id.action_productDetails_to_cartFragment2)


                }
                R.id.btn_add_to_cart ->{
                    viewModel.createCartList(
                        userID = dataStoreManager.userId,
                        basePrice = mProductDetails?.price,
                        productID = mProductDetails?.productId ?: String(),
                        quantity = 1,
                        totalPrice = mProductDetails?.price
                    )

                    Log.d("ProductDetailsFragment", "User ID: ${dataStoreManager.userId}")
                }
            }
        }
    }

    private fun observerViewModel() {
        viewModel.createCart.observe(viewLifecycleOwner) { response ->
            response?.let { result ->
                binding.progressBar.isVisible = result.isLoading()
                when {
                    result.isLoading() -> {
                        binding.btnAddToCart.text = getString(R.string.loading)
                    }
                    result.isSuccess() -> {
                        addToCartSuccess()
                        Log.d("cart added", "cart added: ${addToCartSuccess()}")
                    }
                    result.isError() -> {
                        val errorMessage = result.message
                        binding.btnAddToCart.text = getString(R.string.loading)
                        AlertDialog.Builder(requireContext())
                            .setTitle("Failed")
                            .setIcon(R.drawable.baseline_error_24)
                            .setMessage(errorMessage)
                            .setPositiveButton(R.string.okay) { _, _ ->
                                // do nothing
                            }
                            .show()
                        Log.d("cart error", "cart added: ${errorMessage}")
                        binding.btnAddToCart.visibility = View.GONE
                    }
                }
            }
        }
    }

    fun addToCartSuccess() {
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.success_message_item_added_to_cart),
            Toast.LENGTH_SHORT
        ).show()

        // Hide the AddToCart button if the item is already in the cart.
        binding.btnAddToCart.visibility = View.GONE
        // Show the GoToCart button if the item is already in the cart. User can update the quantity from the cart list screen if he wants.
        binding.btnGoToCart.visibility = View.VISIBLE
    }


    fun productDetailsSuccess(product: Product) {
        mProductDetails = product

        // Populate the product details in the UI.
        GlideLoader(requireContext()).loadProductPicture(
            product.images,
            binding.ivProductDetailImage
        )

        binding.tvProductDetailsTitle.text = product.name
        binding.tvProductDetailsPrice.text = "₦${product.price}"
        binding.tvProductDetailsDescription.text = product.description
        binding.tvProductDetailsStockQuantity.text = product.quantity.toString()
        binding.tvProductSizeSmallBtn.text = product.variant.toString()
        binding.tvProductSizeMediumBtn.text = product.variant.toString()
        // binding.commentTextView.text = productArg
        binding.usernameTextView.text = product.name
        binding.ratingBar.apply {
           rating = product.averageRating.toFloat()
        }

        if (product.quantity.toInt() == 0) {
            // Hide the AddToCart button if the item is already in the cart.
            binding.btnGoToCart.visibility = View.GONE

            binding.tvProductDetailsStockQuantity.text =
                resources.getString(R.string.lbl_out_of_stock)

            binding.tvProductDetailsStockQuantity.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorSnackBarError
                )
            )
        } else {
//            binding.btnAddToCart.setOnClickListener {
//                viewModel.createCartList(
//                    userID = dataStoreManager.userId,
//                    basePrice = productArg!!.price,
//                    productID = productArg!!.productId,
//                    quantity = 1,
//                    totalPrice = productArg!!.price
//                )
//
//            }
        }
    }
}