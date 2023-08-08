package com.example.tapshopping.ui.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tapshopping.R
import com.example.tapshopping.data.local.DataStoreManager
import com.example.tapshopping.data.model.CartDelete
import com.example.tapshopping.data.model.CartProduct
import com.example.tapshopping.databinding.FragmentCartBinding
import com.example.tapshopping.ui.adapter.CartAdapterList
import com.example.tapshopping.ui.viewModel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : Fragment(), CartAdapterList.CartItemListener {
    private lateinit var binding: FragmentCartBinding
    private lateinit var cartAdapterList: CartAdapterList
    private var mProductId: String = ""

    @Inject
    lateinit var dataStoreManager: DataStoreManager
    private val cartItemViewModel by viewModels<CartViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cartAdapterList = CartAdapterList(this)
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCartAdapter()
        observeCartViewModel()
        cartItemViewModel.getCartItems()
        // cartItemViewModel.deleteCartItem(mProductId,userID = dataStoreManager.userId)
        binding.toolbarCartListActivity.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.llCheckout.visibility = View.VISIBLE

        binding.btnPlaceOrder.setOnClickListener {
            Toast.makeText(requireContext(), "Checkout", Toast.LENGTH_SHORT).show()
        }


    }

    private fun setUpCartAdapter() {
        val recyclerView: RecyclerView = binding.rvCartItemsList
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapterList
            adapter
        }
    }

    private fun observeCartViewModel() {
        // Observe the cart items
        cartItemViewModel.cartItems.observe(viewLifecycleOwner) { cartData ->
            cartData?.let { result ->
                when {
                    result.isSuccess() -> {
                        binding.rvCartItemsList.visibility = View.VISIBLE
                        binding.tvNoCartItemFound.visibility = View.GONE
                        val cartList = result.data!!.content.data.products
                        cartAdapterList.updateCart(cartList)

                        // Call the totalPriceOfProduct function to calculate and update the UI
                        if (cartList.isNotEmpty()) {
                            val cartProduct =
                                cartList[0] // Assuming you want to calculate the total for the first cart item
                            totalPriceOfProduct(cartProduct)
                        }

                    }

                    result.isLoading() -> {
                        binding.progressBar2.isVisible = false
                    }

                    result.isError() -> {
                        binding.progressBar2.isVisible = false
                        binding.tvNoCartItemFound.visibility = View.VISIBLE
                        binding.tvNoCartItemFound.visibility = View.VISIBLE
                        val errorMessage = result.message ?: "Unknown error"
                        binding.tvNoCartItemFound.text = errorMessage
                        Log.e("cart error", "Failed to fetch cart items: $errorMessage")
                    }
                }
            }
        }

        // Observe the delete cart operation
        cartItemViewModel.deleteCart.observe(viewLifecycleOwner) { deleteStatus ->
            deleteStatus?.let { result ->
                when {
                    result.isSuccess() -> {
                        // Cart item deleted successfully
                        AlertDialog.Builder(requireContext()).setTitle("Successful")
                            .setIcon(R.drawable.successful)
                            .setMessage(result.data!!.content.data.createdAt)
                            .setPositiveButton("Deleted") { _, _ ->
                                findNavController().navigateUp()
                            }.show()
                    }


                    result.isError() -> {
                        // Handle error during cart item deletion
                        val errorMessage = result.message ?: "Unknown error"
                        AlertDialog.Builder(requireContext()).setTitle("Failed")
                            .setIcon(R.drawable.baseline_error_24)
                            .setMessage(errorMessage)
                            .setPositiveButton(R.string.retry) { _, _ ->
                                // do nothing
                            }.show()
                        Log.e("cart delete error", "Failed to delete cart item: $errorMessage")
                    }
                }
                binding.progressBar2.isVisible = result.isLoading()

            }
        }
    }

    fun deleteCartItem(cartItem: CartProduct) {
        cartItemViewModel.deleteCartItem()
    }

    override fun onDeleteCartItem(cartItem: CartProduct) {
        deleteCartItem(cartItem)
    }

    @SuppressLint("SetTextI18n")
    private fun totalPriceOfProduct(cart: CartProduct) {
        // Get the sub-total from the cart
        val cartQuantity = cart.quantity
//        var subTotal =   "₦${cart.basePrice * cartQuantity}"
        var subTotal: Double = 560.0
        val shippingCharge = 3500.00
        val totalAmount = cartQuantity + subTotal + shippingCharge
        val availableQuantity = cart.quantity.toInt()
        if (availableQuantity > 0) {
            val price = cart.basePrice.toDouble()
            val quantity = cartQuantity
            subTotal += (price * quantity)
        }
        // Update the UI with the calculated values
        binding.tvSubTotal.text = "₦$subTotal"
        binding.tvShippingCharge.text = "₦$shippingCharge"
        binding.tvTotalAmount.text = "₦$totalAmount"
    }

}
