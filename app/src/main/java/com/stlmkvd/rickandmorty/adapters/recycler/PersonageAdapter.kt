package com.stlmkvd.rickandmorty.adapters.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stlmkvd.rickandmorty.R
import com.stlmkvd.rickandmorty.data.Personage
import com.stlmkvd.rickandmorty.databinding.ViewholderPersonageBinding
import com.stlmkvd.rickandmorty.model.PersonagesViewModel

open class PersonageAdapter(
    private val viewModel: PersonagesViewModel,
    urls: List<String>? = null
) :
    BaseAdapter<Personage>(viewModel, urls) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractViewHolder {
        val binding =
            ViewholderPersonageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return PersonageViewHolder(binding)
    }

    private inner class PersonageViewHolder(private val binding: ViewholderPersonageBinding) :
        AbstractViewHolder(binding.root) {

        override fun bind(item: Personage) {
            binding.image.setImageBitmap(null)
            binding.progressBar.visibility = View.VISIBLE
            viewModel.loadImage(item.imageUrl, item.imageFileName) {
                if (it == null) {
                    binding.image.setImageResource(R.drawable.download_image_error)
                }
                else binding.image.setImageBitmap(it)
                binding.progressBar.visibility = View.GONE
            }
            binding.personage = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onItemClickListener?.onClick(item)
            }
        }
    }
}