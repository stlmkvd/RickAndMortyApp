package com.stlmkvd.rickandmorty.fragments.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stlmkvd.rickandmorty.data.Personage
import com.stlmkvd.rickandmorty.databinding.FragmentPersonagesFiltersBinding

const val REQUEST_KEY_SUBMIT_SELECTIONS = "SUBMIT_SELECTIONS"
const val ARG_SELECTION = "ARG_SELECTION"
private const val TAG = "PersonagesFiltersFragment"

class PersonagesFiltersFragment : Fragment() {

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
        }
        binding.btnClear.setOnClickListener {
            clearSelections()
        }
    }

    private fun submitSelections() {
        val selection = Personage.PersonageFilterSelection(
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
            putSerializable(ARG_SELECTION, selection)
        }
        parentFragmentManager.setFragmentResult(REQUEST_KEY_SUBMIT_SELECTIONS, bundle)
    }

    private fun clearSelections() {
        with(binding) {
            etNameHolder.editText!!.text.clear()
            cbFilterStatusAlive.isChecked = true
            cbFilterStatusDead.isChecked = true
            cbFilterStatusUnknown.isChecked = true
            etSpeciesHolder.editText!!.text.clear()
            etTypeHolder.editText!!.text.clear()
        }
        val bundle = Bundle().apply { putSerializable(ARG_SELECTION, null) }
        parentFragmentManager.setFragmentResult(REQUEST_KEY_SUBMIT_SELECTIONS, bundle)
    }
}