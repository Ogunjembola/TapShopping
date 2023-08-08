package com.example.tapshopping.ui.adapter

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tapshopping.R
import com.example.tapshopping.data.model.CartData
import com.example.tapshopping.data.model.CartProduct

import com.example.tapshopping.databinding.ItemCartLayoutBinding
import com.example.tapshopping.ui.fragment.CartFragment

class CartAdapterList(private val listener: CartItemListener) :
    RecyclerView.Adapter<CartAdapterList.CartViewHolder>() {
    var cartList: List<CartProduct> = mutableListOf()
    private var selectedItemPosition: Int = RecyclerView.NO_POSITION
    fun updateCart(newCart: List<CartProduct>) {
        cartList = newCart
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {
        return CartViewHolder(
            ItemCartLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cart = cartList[position]
        holder.bindCart(cart)

        val tvCartItemPrice = holder.item.tvCartItemPrice
        val tvCartQuantity = holder.item.tvCartQuantity
        var cartQuantity = cart.quantity
        tvCartQuantity.text = cartQuantity.toString()
        tvCartItemPrice.text = "₦${cart.basePrice * cartQuantity}"

        // Set OnClickListener for the remove button
        holder.item.ibRemoveCartItem.setOnClickListener {
            if (cartQuantity > 0) {
                cartQuantity--
                tvCartQuantity.text = cartQuantity.toString()
                tvCartItemPrice.text = "₦${cart.basePrice * cartQuantity}"
            }
        }

        // Set OnClickListener for the add button
        holder.item.ibAddCartItem.setOnClickListener {
            cartQuantity++
            tvCartQuantity.text = cartQuantity.toString()
            tvCartItemPrice.text = "₦${cart.basePrice * cartQuantity}"
        }

        // Set OnClickListener for the delete button
        holder.item.ibDeleteCartItem.setOnClickListener {
            listener.onDeleteCartItem(cart)
        }

        // Set OnClickListener for the whole item view
        holder.itemView.setOnClickListener {
            setSelectedItemPosition(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    fun getSelectedItem(): CartProduct? {
        if (selectedItemPosition != RecyclerView.NO_POSITION) {
            return cartList[selectedItemPosition]
        }
        return null
    }

    private fun setSelectedItemPosition(position: Int) {
        selectedItemPosition = position
    }

    class CartViewHolder(val item: ItemCartLayoutBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun bindCart(cart: CartProduct) {
            item.tvCartItemPrice.text =  "₦${cart.basePrice}"
            item.tvCartQuantity.text = cart.quantity.toString()
            item.tvCartItemTitle.text = cart.productID.name
            val color: String? = cart.productID.variant.firstOrNull()?.colour?.firstOrNull()
            item.tvColor.text = color ?: "No Color"
            item.tvSize.text = cart.productID.variant.size.toString()


        }
    }

    interface CartItemListener {
        fun onDeleteCartItem(cartItem: CartProduct)
    }
}