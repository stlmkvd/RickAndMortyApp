package com.stlmkvd.rickandmorty.fragments.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stlmkvd.rickandmorty.data.DataItem
import com.stlmkvd.rickandmorty.data.Episode
import com.stlmkvd.rickandmorty.databinding.FragmentEpisodesFiltersBinding
import com.stlmkvd.rickandmorty.fragments.list.REQUEST_KEY_SUBMIT_FILTERS

private const val TAG = "PersonagesFiltersFragment"

class EpisodesFiltersFragment : BaseFiltersFragment<Episode>() {

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
            requestClosePanel()
        }
        binding.btnClear.setOnClickListener {
            clearSelections()
            requestClosePanel()
        }
    }

    override fun clearSelections() {
        binding.etNameHolder.editText!!.text.clear()
        binding.etEpisodeCodeHolder.editText!!.text.clear()
        parentFragmentManager.setFragmentResult(REQUEST_KEY_SUBMIT_FILTERS, Bundle.EMPTY)
    }

    override fun submitSelections() {
        val selection = Episode.EpisodesFilterSelection(
            name = binding.etNameHolder.editText!!.text.toString(),
            episodeCode = binding.etEpisodeCodeHolder.editText!!.text.toString()
        )
        val bundle = Bundle().apply {
            putSerializable(DataItem.FilterSelection.BUNDLE_ARG, selection)
        }
        parentFragmentManager.setFragmentResult(REQUEST_KEY_SUBMIT_FILTERS, bundle)
    }
}