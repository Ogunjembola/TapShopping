package com.example.tapshopping.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tapshopping.data.model.CategoryData
import com.example.tapshopping.databinding.DisplayProductCategoryBinding

class ProductCategoryAdapter(private val selectedCategory: ( CategoryData) -> Unit) :
    RecyclerView.Adapter<ProductCategoryAdapter.ProductCategoryViewHolder>() {

    var categories: List<CategoryData> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCategoryViewHolder {
        val view = DisplayProductCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductCategoryViewHolder(view)
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: ProductCategoryViewHolder, position: Int) {
       val category = categories[position]
        holder.bindItem(category)
    }

    inner class ProductCategoryViewHolder(val binding: DisplayProductCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

       fun bindItem(categoryData: CategoryData){
            binding.apply {
                catNAme.text = categoryData.categoryName
                catDescription.text = categoryData.categoryDescription
                root.setOnClickListener {
                    selectedCategory.invoke(categoryData)
                }
            }
        }
    }
}