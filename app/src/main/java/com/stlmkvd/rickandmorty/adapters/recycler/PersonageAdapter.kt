package com.stlmkvd.rickandmorty.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import com.stlmkvd.rickandmorty.BitmapProvider
import com.stlmkvd.rickandmorty.R
import com.stlmkvd.rickandmorty.data.Personage
import com.stlmkvd.rickandmorty.databinding.ViewholderPersonageBinding
import com.stlmkvd.rickandmorty.model.PersonagesViewModel
import com.stlmkvd.rickandmorty.viewholders.AbstractViewHolder
import com.stlmkvd.rickandmorty.viewholders.PersonageViewHolder

class PersonageAdapter(private val viewModel: PersonagesViewModel, urls: List<String>? = null) :
    AbstractAdapter<Personage>(viewModel, urls) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractViewHolder<Personage> {
        val binding =
            ViewholderPersonageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return PersonageViewHolder(binding)
    }
}