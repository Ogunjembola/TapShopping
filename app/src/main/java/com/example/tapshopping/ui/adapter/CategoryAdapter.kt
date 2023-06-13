package com.example.tapshopping.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tapshopping.data.model.Category
import com.example.tapshopping.databinding.ItemCategoriesBinding

class CategoryAdapter(private val categories: List<Category> = emptyList()):RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
                ItemCategoriesBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind.apply {
//            imgCat.setImageResource(category.categoryData.description)
            categoryName.text = category.categoryData.name
        }
    }
    class CategoryViewHolder(binding: ItemCategoriesBinding) : RecyclerView.ViewHolder(binding.root){
        val bind = binding
    }

}