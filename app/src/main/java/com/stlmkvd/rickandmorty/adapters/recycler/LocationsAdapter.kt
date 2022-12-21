package com.stlmkvd.rickandmorty.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import com.stlmkvd.rickandmorty.data.Location
import com.stlmkvd.rickandmorty.databinding.ViewholderLocationBinding
import com.stlmkvd.rickandmorty.model.LocationsViewModel
import com.stlmkvd.rickandmorty.viewholders.AbstractViewHolder
import com.stlmkvd.rickandmorty.viewholders.LocationViewHolder


class LocationsAdapter(private val viewModel: LocationsViewModel, urls: List<String>? = null) :
    AbstractAdapter<Location>(viewModel, urls) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<Location> {
        val binding =
            ViewholderLocationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return LocationViewHolder(binding)
    }
}