package com.rawat.dagger2.mvvm.dagger.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rawat.dagger2.databinding.ItemViewHomeBinding
import com.rawat.dagger2.mvvm.dagger.models.Product

class HomeFragmentAdapter :
    RecyclerView.Adapter<HomeFragmentAdapter.HomeViewHolder>() {

    inner class HomeViewHolder(var binding: ItemViewHomeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            ItemViewHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val data = asyncListDiffer.currentList[position]
        holder.binding.apply {
            textViewDescription.text = data.description
            textViewTitle.text = data.title
            Glide
                .with(holder.itemView.context)
                .load(data.image)
                .centerCrop()
                .into(imageViewHome)
        }
    }

    override fun getItemCount() = asyncListDiffer.currentList.size


    private val diffUtil = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product):
                Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product):
                Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    fun saveData(dataResponse: List<Product>) {
        asyncListDiffer.submitList(dataResponse)
    }
}



