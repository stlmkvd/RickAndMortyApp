package com.stlmkvd.rickandmorty.fragments.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stlmkvd.rickandmorty.data.Location
import com.stlmkvd.rickandmorty.databinding.FragmentLocationDetailsBinding
import com.stlmkvd.rickandmorty.fragments.list.EpisodesListFragment
import com.stlmkvd.rickandmorty.fragments.list.PersonagesListFragment

class LocationDetailsFragment : BaseDetailsFragment<Location>() {

    private lateinit var binding: FragmentLocationDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationDetailsBinding.inflate(inflater, container, false)
        binding.location = item
        binding.executePendingBindings()
        childFragmentManager.beginTransaction().replace(
            binding.containerList.id,
            PersonagesListFragment.newInstance(item.residentsUrls as ArrayList<String>)
        ).commit()
        return binding.root
    }

    companion object Factory {
        fun createInstance(bundle: Bundle): LocationDetailsFragment =
            LocationDetailsFragment().apply {
                arguments = bundle
            }
    }
}