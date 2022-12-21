package com.stlmkvd.rickandmorty.viewholders

import com.stlmkvd.rickandmorty.data.Episode
import com.stlmkvd.rickandmorty.databinding.ViewholderEpisodeBinding

class EpisodeViewHolder(private val binding: ViewholderEpisodeBinding) :
    AbstractViewHolder<Episode>(binding.root) {

    override fun bind(item: Episode) {
        binding.episode = item
        binding.executePendingBindings()
    }
}