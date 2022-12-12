package com.stlmkvd.rickandmorty.fragments.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stlmkvd.rickandmorty.data.Personage
import com.stlmkvd.rickandmorty.databinding.FragmentPersonageDetailsBinding


class PersonagesDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPersonageDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val personage = arguments?.getSerializable("personage") as? Personage
            ?: throw java.lang.IllegalStateException("you should pass personage as argument")
        binding = FragmentPersonageDetailsBinding.inflate(inflater, container, false)
        binding.personage = personage
        binding.executePendingBindings()
        return binding.root
    }

    companion object {
        fun newInstance(personage: Personage) =
            PersonagesDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("personage", personage)
                }
            }
    }
}