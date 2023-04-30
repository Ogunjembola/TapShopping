package com.example.tapshopping.data.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tapshopping.data.model.Product
import com.example.tapshopping.databinding.ItemCategoriesBinding
import com.example.tapshopping.utillz.GlideLoader

class HomeAdapter(private val context: ArrayList<Product>):RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
                ItemCategoriesBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]
        if (holder is MyViewHolder) {


//            holder.itemView.title.text = model.title
            holder.bind(model)

        }
    }
    class MyViewHolder(private val binding: ItemCategoriesBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(product: Product){
            binding.title.text = product.title
            GlideLoader(binding.catIcon.context).loadProductPicture(
                product.image,
                binding.catIcon
            )
        }

    }

}