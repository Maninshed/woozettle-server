package com.zettle.payments.android.kotlin_example.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zettle.payments.android.kotlin_example.databinding.ItemCategoryGridBinding
import com.zettle.payments.android.kotlin_example.model.Category

class CategoryAdapter(
    private val categories: List<Category>,
    private val onClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(private val binding: ItemCategoryGridBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.categoryName.text = category.name
            Glide.with(binding.root.context)
                .load(category.image)
                .into(binding.categoryImage)

            binding.root.setOnClickListener {
                onClick(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size
}

