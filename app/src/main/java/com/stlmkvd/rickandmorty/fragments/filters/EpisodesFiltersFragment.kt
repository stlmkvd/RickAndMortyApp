package com.stlmkvd.rickandmorty.fragments.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stlmkvd.rickandmorty.data.Episode
import com.stlmkvd.rickandmorty.databinding.FragmentEpisodesFiltersBinding

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
        binding.btnClear.setOnClickListener {
            clearSelections()
        }
    }

    private fun clearSelections() {
        binding.etNameHolder.editText!!.text.clear()
        binding.etEpisodeCodeHolder.editText!!.text.clear()
        parentFragmentManager.setFragmentResult(REQUEST_KEY_SUBMIT_SELECTIONS, Bundle.EMPTY)
    }

    private fun submitSelections() {
        val selection = Episode.EpisodesFilterSelection(
            name = binding.etNameHolder.editText!!.text.toString(),
            episodeCode = binding.etEpisodeCodeHolder.editText!!.text.toString()
        )
        val bundle = Bundle().apply {
            putSerializable(ARG_SELECTION, selection)
        }
        parentFragmentManager.setFragmentResult(REQUEST_KEY_SUBMIT_SELECTIONS, bundle)
    }
}