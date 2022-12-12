package com.stlmkvd.rickandmorty.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import com.stlmkvd.rickandmorty.data.Episode
import com.stlmkvd.rickandmorty.databinding.ViewholderEpisodeBinding
import com.stlmkvd.rickandmorty.model.AbstractVM
import com.stlmkvd.rickandmorty.model.EpisodesViewModel

class EpisodesAdapter(private val viewModel: EpisodesViewModel) : AbstractAdapter<Episode>(viewModel) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<Episode> {
        val binding =
            ViewholderEpisodeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return EpisodeViewHolder(binding)
    }


    inner class EpisodeViewHolder(private val binding: ViewholderEpisodeBinding) :
       AbstractViewHolder<Episode>(binding.root) {

        override fun bind(item: Episode) {
            binding.episode = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onItemClickListener?.onClick(item)
            }
        }
    }
}