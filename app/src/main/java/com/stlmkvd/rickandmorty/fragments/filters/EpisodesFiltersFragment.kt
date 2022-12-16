package com.stlmkvd.rickandmorty.fragments.filters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stlmkvd.rickandmorty.data.Episode
import com.stlmkvd.rickandmorty.data.Personage
import com.stlmkvd.rickandmorty.databinding.FragmentEpisodesFiltersBinding
import com.stlmkvd.rickandmorty.databinding.FragmentPersonagesFiltersBinding

private const val TAG = "PersonagesFiltersFragment"

class EpisodesFiltersFragment : Fragment() {

    private lateinit var binding: FragmentEpisodesFiltersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodesFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSubmit.setOnClickListener {
            submitSelections()
        }
    }

    private fun submitSelections() {
        val selection = Episode.EpisodesFilterSelection(
            name = binding.etFilterName.text.toString(),
            episodeCode = binding.etFilterEpisode.text.toString()
        )
        val bundle = Bundle().apply {
            putSerializable(ARG_SELECTION, selection)
        }
        parentFragmentManager.setFragmentResult(SUBMIT_SELECTIONS_REQUEST_KEY, bundle)
    }
}