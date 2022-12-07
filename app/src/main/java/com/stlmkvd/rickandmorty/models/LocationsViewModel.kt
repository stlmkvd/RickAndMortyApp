package com.stlmkvd.rickandmorty.models

import androidx.lifecycle.ViewModel
import com.stlmkvd.rickandmorty.Repository
import com.stlmkvd.rickandmorty.data.ItemsProvider
import com.stlmkvd.rickandmorty.data.Location

class LocationsViewModel : ViewModel() {

    private val repository = Repository.getInstance()
    private val locationsProvider = ItemsProvider<Location>() {
        repository.getLocationsPagedSync(it)
    }

    fun getItemAt(position: Int): Location {
        return locationsProvider.getItemAt(position)
    }

    fun getItemCount(): Int {
        return locationsProvider.getItemCount()
    }

    fun registerDataSetChangesCallback(callback: ItemsProvider.CallBack) {
        locationsProvider.registerCallback(callback)
    }

    fun removeDataSetChangesCallback(callback: ItemsProvider.CallBack) {
        locationsProvider.removeCallback(callback)
    }
}