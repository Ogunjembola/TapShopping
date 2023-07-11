package com.example.tapshopping.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tapshopping.R
import com.example.tapshopping.data.local.DataStoreManager
import com.example.tapshopping.data.model.CartProduct
import com.example.tapshopping.data.model.ResponseDataClass
import com.example.tapshopping.databinding.FragmentCartBinding
import com.example.tapshopping.ui.adapter.CartAdapterList
import com.example.tapshopping.ui.viewModel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : Fragment(),CartAdapterList.CartItemListener {
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
        cartItemViewModel.deleteCartItem(mProductId)

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
                        binding.rvCartItemsList.visibility = View.VISIBLE
                        val cartList = result.data!!.content.data
                        cartAdapterList.updateCart(cartList)
                    }

                    result.isLoading() -> {
                        binding.progressBar2.isVisible = true
                    }

                    result.isError() -> {
                        binding.progressBar2.isVisible = false
                        binding.listError.visibility = View.VISIBLE
                        val errorMessage = result.message ?: "Unknown error"
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
                        showDeleteSuccessMessage()
                    }

                    result.isLoading() -> {
                        // Show loading progress
                        binding.progressBar2.isVisible = true
                    }

                    result.isError() -> {
                        // Handle error during cart item deletion
                        val errorMessage = result.message ?: "Unknown error"
                        Log.e("cart delete error", "Failed to delete cart item: $errorMessage")
                    }
                }
            }
        }
    }

     fun deleteCartItem() {
        val selectedCartItem = cartAdapterList.getSelectedItem()
        val cartItemId = selectedCartItem?.productID

        if (cartItemId != null) {
            cartItemViewModel.deleteCartItem(cartItemId)
        } else {
            Toast.makeText(requireContext(), "No cart item selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDeleteSuccessMessage() {
        Toast.makeText(requireContext(), "Cart item deleted successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteCartItem(cartItem: CartProduct) {
        deleteCartItem()
    }
}
