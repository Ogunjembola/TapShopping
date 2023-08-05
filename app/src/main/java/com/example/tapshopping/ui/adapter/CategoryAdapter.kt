package com.example.tapshopping.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.example.tapshopping.R
import com.example.tapshopping.data.model.CategoryData
import com.example.tapshopping.databinding.DisplayCategoryBinding

class CategoryAdapter(
    private val itemSelected: (CategoryData) -> Unit,
    private val showOptionMenu: (Boolean) -> Unit
) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    var categories: ArrayList<CategoryData> = ArrayList()
    private var currentSelectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            DisplayCategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]

        if (category.isSelected) {
            holder.bind.markSelected.visibility = View.VISIBLE
            holder.bind.categoryCard.setCardBackgroundColor(
                getColor(
                    holder.itemView.context,
                    R.color.colorDarkGrey
                )
            )
        } else {
            holder.bind.markSelected.visibility = View.GONE
            holder.bind.categoryCard.setCardBackgroundColor(
                getColor(
                    holder.itemView.context,
                    R.color.colorOffWhite
                )
            )

        }

        holder.bind.apply {
            categoryName.text = category.categoryName
            catDescription.text = category.categoryDescription
            categoryCard.setOnLongClickListener { markSelectedItem(position) }
            categoryCard.setOnClickListener { deSelectItemSelected(position) }

        }
    }

    private fun deSelectItemSelected(position: Int) {
        if (currentSelectedPosition == position) {
            currentSelectedPosition = -1
            categories[position].isSelected = false
            notifyDataSetChanged()
            showOptionMenu.invoke(false)
        }
    }

    private fun markSelectedItem(position: Int): Boolean {
        for (item in categories) {
            item.isSelected = false
        }
        categories[position].isSelected = true
        currentSelectedPosition = position
        notifyDataSetChanged()
        showOptionMenu.invoke(true)
        itemSelected.invoke(categories[position])

        return true
    }

    class CategoryViewHolder(binding: DisplayCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val bind = binding
    }

}