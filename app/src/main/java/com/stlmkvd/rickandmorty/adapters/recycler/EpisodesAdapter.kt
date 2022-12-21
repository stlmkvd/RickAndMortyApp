package com.stlmkvd.rickandmorty.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import com.stlmkvd.rickandmorty.data.Episode
import com.stlmkvd.rickandmorty.databinding.ViewholderEpisodeBinding
import com.stlmkvd.rickandmorty.model.EpisodesViewModel
import com.stlmkvd.rickandmorty.viewholders.AbstractViewHolder
import com.stlmkvd.rickandmorty.viewholders.EpisodeViewHolder

class EpisodesAdapter(private val viewModel: EpisodesViewModel, urls: List<String>? = null) :
    AbstractAdapter<Episode>(viewModel, urls) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<Episode> {
        val binding =
            ViewholderEpisodeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return EpisodeViewHolder(binding)
    }
}