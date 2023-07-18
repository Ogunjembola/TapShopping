package com.example.tapshopping.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tapshopping.R
import com.example.tapshopping.data.model.Product
import com.example.tapshopping.databinding.ItemProductBinding

class ProductAdapterList(
    private val onItemClick: (Product) -> Unit

) : RecyclerView.Adapter<ProductAdapterList.ProductViewHolder>() {
    var productList: List<Product> = mutableListOf()
    fun updateProduct(newProduct: List<Product>) {
        productList = newProduct
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bindData(product)
        holder.item.apply {
            root.setOnClickListener {
                onItemClick(product)
            }
        }
    }

    override fun getItemCount() = productList.size


    class ProductViewHolder(val item: ItemProductBinding) : RecyclerView.ViewHolder(item.root) {

        fun bindData(product: Product) {
            item.apply {
                // Set the product image using the first image URL from the list
                val imageUrl = product.images.firstOrNull()
                if (imageUrl != null) {
                    // Load the image using your preferred image loading library
                    // For example, if you're using Glide:
                    Glide.with(productImage.context).load(imageUrl).into(productImage)
                } else {
                    // Handle the case when there is no image available
                    productImage.setImageResource(R.drawable.ic_user_placeholder)
                }

                productPrice.text = product.price.toString()
                productTitle.text = product.name
            }
        }
    }
}
