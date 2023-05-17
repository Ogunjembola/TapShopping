package com.example.tapshopping.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tapshopping.data.model.Product
import com.example.tapshopping.databinding.ItemProductBinding

class ProductAdapter(private val products: List<Product> = emptyList()) :
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

        fun bindData(product: Product) {
            item.apply {
                productImage.setImageResource(product.noOfRatings)
                productDescription.text = product.description
                productTitle.text = product.name
            }
        }
    }

}