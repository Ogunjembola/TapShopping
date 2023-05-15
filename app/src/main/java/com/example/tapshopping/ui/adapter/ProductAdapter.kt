package com.example.tapshopping.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tapshopping.data.model.ProductDummy
import com.example.tapshopping.databinding.ItemProductBinding

class ProductAdapter(private val products: List<ProductDummy> = emptyList()) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bindData(product)
    }

    override fun getItemCount() = products.size


    class ProductViewHolder(private val item: ItemProductBinding) :
        RecyclerView.ViewHolder(item.root) {

        fun bindData(product: ProductDummy) {
            item.apply {
                productImage.setImageResource(product.image)
                productDescription.text = product.description
                productTitle.text = product.title
            }
        }
    }

}