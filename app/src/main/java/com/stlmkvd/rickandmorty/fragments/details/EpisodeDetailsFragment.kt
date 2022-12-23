package com.stlmkvd.rickandmorty.fragments.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stlmkvd.rickandmorty.REQUEST_KEY_OPEN_DETAILS
import com.stlmkvd.rickandmorty.data.Episode
import com.stlmkvd.rickandmorty.databinding.FragmentEpisodeDetailsBinding
import com.stlmkvd.rickandmorty.fragments.list.EpisodesListFragment
import com.stlmkvd.rickandmorty.fragments.list.PersonagesListFragment

class EpisodeDetailsFragment : BaseDetailsFragment<Episode>() {


    private lateinit var binding: FragmentEpisodeDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodeDetailsBinding.inflate(inflater, container, false)
        binding.episode = item
        binding.executePendingBindings()
        childFragmentManager.beginTransaction().replace(
            binding.containerList.id,
            PersonagesListFragment.newInstance(item.personageUrls as ArrayList<String>)
        ).commit()
        return binding.root
    }

    companion object Factory {
        fun createInstance(bundle: Bundle): EpisodeDetailsFragment =
            EpisodeDetailsFragment().apply {
                arguments = bundle
            }
    }
}