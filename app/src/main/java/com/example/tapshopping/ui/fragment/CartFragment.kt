package com.example.tapshopping.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tapshopping.R
import com.example.tapshopping.data.local.DataStoreManager
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
    private var cartList: List<CartProduct> = emptyList()
    private var totalAmount: Double = 0.0


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
        binding.toolbarCartListActivity.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.llCheckout.visibility = View.VISIBLE

        binding.btnPlaceOrder.setOnClickListener {
            val cartProduct = cartList.getOrNull(0)

            if (cartProduct != null) {
                val bundle = Bundle()
                bundle.putDouble("totalPrice", totalAmount)
                bundle.putSerializable("cartProduct", cartProduct) // Pass cartProduct as Serializable

                findNavController().navigate(R.id.action_cartFragment_to_paymentFragment, bundle)
            }
        }

    }

    private fun setUpCartAdapter() {
        val recyclerView: RecyclerView = binding.rvCartItemsList
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapterList
        }
    }

    private fun observeCartViewModel() {
        // Observe the cart items
        cartItemViewModel.cartItems.observe(viewLifecycleOwner) { cartData ->
            cartData?.let { result ->
                when {
                    result.isSuccess() -> {
                        binding.progressBar2.isVisible = false
                        binding.rvCartItemsList.visibility = View.VISIBLE
                        binding.tvNoCartItemFound.visibility = View.GONE
                        val cartList = result.data!!.content?.data?.products
                        if (cartList != null) {
                            cartAdapterList.updateCart(cartList)
                            this.cartList = cartList
                        }

                    }

                    result.isLoading() -> {
                        binding.progressBar2.isVisible = true
                    }

                    result.isError() -> {
                        binding.progressBar2.isVisible = false
                        binding.rvCartItemsList.visibility = View.GONE
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
                        AlertDialog.Builder(requireContext())
                            .setTitle("Successful")
                            .setIcon(R.drawable.successful)
                            .setMessage(result.data!!.content?.data?.createdAt ?: "")
                            .setPositiveButton("Deleted") { _, _ ->
                                // Refresh the cart list after successful deletion
                                cartItemViewModel.getCartItems()
                            }
                            .show()
                    }

                    result.isError() -> {
                        // Handle error during cart item deletion
                        val errorMessage = result.message ?: "Unknown error"
                        AlertDialog.Builder(requireContext())
                            .setTitle("Failed")
                            .setIcon(R.drawable.baseline_error_24)
                            .setMessage(errorMessage)
                            .setPositiveButton(R.string.retry) { _, _ ->
                                // do nothing
                            }
                            .show()
                        Log.e("cart delete error", "Failed to delete cart item: $errorMessage")
                    }
                }

                // Hide the progress bar after processing the delete operation
                binding.progressBar2.isVisible = false
            }
        }

    }

    fun deleteCartItem(cartItem: CartProduct) {
        cartItemViewModel.deleteCartItem()
    }
    override fun onDeleteCartItem(cartItem: CartProduct) {
        deleteCartItem(cartItem)
    }

    override fun onCartItemAdded(price: Double) {
        binding.tvSubTotal.text = "₦$price"
        val subTotal = price
        val shippingCharge = 3500.0
         totalAmount = subTotal + shippingCharge

        val formattedSubTotal = "₦$subTotal"
        val formattedShippingCharge = "₦$shippingCharge"
        val formattedTotalAmount = "₦$totalAmount"

        // Update the UI with the formatted values
        binding.tvSubTotal.text = formattedSubTotal
        binding.tvShippingCharge.text = formattedShippingCharge
        binding.tvTotalAmount.text = formattedTotalAmount
    }

}
