package com.stlmkvd.rickandmorty.models

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.stlmkvd.rickandmorty.Repository
import com.stlmkvd.rickandmorty.data.Personage
import com.stlmkvd.rickandmorty.data.ItemsProvider

class PersonageViewModel : ViewModel() {

    private val repository = Repository.getInstance()
    val personagesProvider = ItemsProvider<Personage>() {
        repository.getPersonagesPagedSync(it)
    }

    fun getItemAt(position: Int): Personage {
        return personagesProvider.getItemAt(position)
    }

    fun loadImageSync(url: String): Bitmap {
        return repository.downloadImageSync(url)
    }

    fun getItemCount(): Int {
        return personagesProvider.getItemCount()
    }

    fun registerDataSetChangesCallback(callback: ItemsProvider.CallBack) {
        personagesProvider.registerCallback(callback)
    }

    fun removeDataSetChangesCallback(callback: ItemsProvider.CallBack) {
        personagesProvider.removeCallback(callback)
    }
}