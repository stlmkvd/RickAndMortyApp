package com.stlmkvd.rickandmorty.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import com.stlmkvd.rickandmorty.data.Episode
import com.stlmkvd.rickandmorty.databinding.ViewholderEpisodeBinding

class EpisodesAdapter(private val itemsProvider: ItemsProvider<Episode>, urls: List<String>? = null) :
    BaseAdapter<Episode>(itemsProvider, urls) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        val binding =
            ViewholderEpisodeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return EpisodeViewHolder(binding)
    }

    private inner class EpisodeViewHolder(private val binding: ViewholderEpisodeBinding) :
        AbstractViewHolder(binding.root) {

        override fun bind(item: Episode) {
            binding.episode = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onItemClickListener?.onClick(item)
            }
        }
    }
}