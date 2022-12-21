package com.stlmkvd.rickandmorty.viewholders

import com.stlmkvd.rickandmorty.data.Location
import com.stlmkvd.rickandmorty.databinding.ViewholderLocationBinding

class LocationViewHolder(private val binding: ViewholderLocationBinding) :
    AbstractViewHolder<Location>(binding.root) {

    override fun bind(item: Location) {
        binding.location = item
        binding.executePendingBindings()
    }
}