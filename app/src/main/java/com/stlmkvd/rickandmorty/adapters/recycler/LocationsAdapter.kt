package com.stlmkvd.rickandmorty.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import com.stlmkvd.rickandmorty.data.Location
import com.stlmkvd.rickandmorty.databinding.ViewholderLocationBinding
import com.stlmkvd.rickandmorty.model.AbstractVM
import com.stlmkvd.rickandmorty.model.LocationsViewModel


class LocationsAdapter(private val viewModel: LocationsViewModel) :
    AbstractAdapter<Location>(viewModel) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<Location> {
        val binding =
            ViewholderLocationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return LocationViewHolder(binding)
    }


    inner class LocationViewHolder(private val binding: ViewholderLocationBinding) :
        AbstractViewHolder<Location>(binding.root) {

        override fun bind(item: Location) {
            binding.location = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onItemClickListener?.onClick(item)
            }
        }
    }
}