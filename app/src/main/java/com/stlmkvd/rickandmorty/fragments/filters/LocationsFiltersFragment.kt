package com.stlmkvd.rickandmorty.fragments.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stlmkvd.rickandmorty.data.Location
import com.stlmkvd.rickandmorty.databinding.FragmentLocationsFiltersBinding


class LocationsFiltersFragment : Fragment() {

    private lateinit var binding: FragmentLocationsFiltersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationsFiltersBinding.inflate(inflater, container, false)
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



    private fun submitSelections() {
        val selection = Location.LocationsFilterSelection(
            binding.etNameHolder.editText!!.text.toString(),
            binding.etTypeHolder.editText!!.text.toString(),
            binding.etDimesionHolder.editText!!.text.toString()
        )
        val bundle = Bundle().apply {
            putSerializable(ARG_SELECTION, selection)
        }
        parentFragmentManager.setFragmentResult(REQUEST_KEY_SUBMIT_SELECTIONS, bundle)
    }

    private fun clearSelections() {
        with(binding) {
            etNameHolder.editText!!.text.clear()
            etTypeHolder.editText!!.text.clear()
            etDimesionHolder.editText!!.text.clear()
        }
        parentFragmentManager.setFragmentResult(REQUEST_KEY_SUBMIT_SELECTIONS, Bundle.EMPTY)
    }
}