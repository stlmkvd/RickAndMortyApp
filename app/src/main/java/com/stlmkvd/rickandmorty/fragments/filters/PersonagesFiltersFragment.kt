package com.stlmkvd.rickandmorty.fragments.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stlmkvd.rickandmorty.data.DataItem
import com.stlmkvd.rickandmorty.data.Personage
import com.stlmkvd.rickandmorty.databinding.FragmentPersonagesFiltersBinding
import com.stlmkvd.rickandmorty.fragments.list.REQUEST_KEY_SUBMIT_FILTERS


private const val TAG = "PersonagesFiltersFragment"

class PersonagesFiltersFragment : BaseFiltersFragment<Personage>() {

    private lateinit var binding: FragmentPersonagesFiltersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonagesFiltersBinding.inflate(inflater, container, false)
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

    override fun submitSelections() {
        val selection = Personage.FilterSelection(
            name = binding.etNameHolder.editText!!.text.toString(),
            species = binding.etSpeciesHolder.editText!!.text.toString(),
            type = binding.etTypeHolder.editText!!.text.toString()
        )
        selection.statuses.apply {
            if (binding.cbFilterStatusDead.isChecked) add(Personage.STATUS_DEAD)
            if (binding.cbFilterStatusAlive.isChecked) add(Personage.STATUS_ALIVE)
            if (binding.cbFilterStatusUnknown.isChecked) add(Personage.STATUS_UNKNOWN)
        }
        selection.genders.apply {
            if (binding.cbFilterGenderMale.isChecked) add(Personage.GENDER_MALE)
            if (binding.cbFilterGenderFemale.isChecked) add(Personage.GENDER_FEMALE)
            if (binding.cbFilterGenderGenderless.isChecked) add(Personage.GENDER_GENDERLESS)
            if (binding.cbFilterGenderUnknown.isChecked) add(Personage.GENDER_UNKNOWN)
        }
        val bundle = Bundle().apply {
            putSerializable(DataItem.FilterSelection.BUNDLE_ARG, selection)
        }
        parentFragmentManager.setFragmentResult(REQUEST_KEY_SUBMIT_FILTERS, bundle)
    }

    override fun clearSelections() {
        with(binding) {
            etNameHolder.editText!!.text.clear()
            cbFilterStatusAlive.isChecked = true
            cbFilterStatusDead.isChecked = true
            cbFilterStatusUnknown.isChecked = true
            etSpeciesHolder.editText!!.text.clear()
            etTypeHolder.editText!!.text.clear()
        }
        val bundle = Bundle().apply { putSerializable(DataItem.FilterSelection.BUNDLE_ARG, null) }
        parentFragmentManager.setFragmentResult(REQUEST_KEY_SUBMIT_FILTERS, bundle)
    }
}