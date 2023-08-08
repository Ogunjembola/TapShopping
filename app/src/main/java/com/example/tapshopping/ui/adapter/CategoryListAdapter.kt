package com.example.tapshopping.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tapshopping.data.model.CategoryData
import com.example.tapshopping.databinding.ItemCategoriesBinding

class CategoryListAdapter: RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>() {
    var categoryList: List<CategoryData> = mutableListOf()
    fun updateCategory(newProduct: List<CategoryData>) {
        categoryList = newProduct
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CategoryViewHolder {
        return CategoryViewHolder(
            ItemCategoriesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.bindData(category)

    }

    override fun getItemCount() = categoryList.size


    class CategoryViewHolder(val item: ItemCategoriesBinding) : RecyclerView.ViewHolder(item.root) {

        fun bindData(category: CategoryData) {
            item.apply {
                categoryName.text = category.categoryName
//                val imageUrl = category.images.firstOrNull()
//                if (imageUrl != null) {
//                    // Load the image using your preferred image loading library
//                    // For example, if you're using Glide:
//                    Glide.with(imgCat.context).load(imageUrl).into(imgCat)
//                } else {
//                    // Handle the case when there is no image available
//                    imgCat.setImageResource(R.drawable.ic_user_placeholder)
//                }

            }
        }
    }
}
