package com.stlmkvd.rickandmorty.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import com.stlmkvd.rickandmorty.data.Location
import com.stlmkvd.rickandmorty.databinding.ViewholderLocationBinding


class LocationsAdapter(private val itemsProvider: ItemsProvider<Location> , urls: List<String>? = null) :
    BaseAdapter<Location>(itemsProvider, urls) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        val binding =
            ViewholderLocationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return LocationViewHolder(binding)
    }

    private inner class LocationViewHolder(private val binding: ViewholderLocationBinding) :
        AbstractViewHolder(binding.root) {

        override fun bind(item: Location) {
            binding.location = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onItemClickListener?.onClick(item)
            }
        }
    }
}