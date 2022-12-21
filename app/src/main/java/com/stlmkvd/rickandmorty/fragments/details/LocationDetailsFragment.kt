package com.stlmkvd.rickandmorty.fragments.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.stlmkvd.rickandmorty.BUNDLE_ARG_LOCATION
import com.stlmkvd.rickandmorty.BUNDLE_ARG_PERSONAGE
import com.stlmkvd.rickandmorty.REQUEST_KEY_OPEN_PERSONAGE
import com.stlmkvd.rickandmorty.adapters.recycler.AbstractAdapter
import com.stlmkvd.rickandmorty.adapters.recycler.PersonageAdapter
import com.stlmkvd.rickandmorty.data.Location
import com.stlmkvd.rickandmorty.databinding.FragmentLocationDetailsBinding
import com.stlmkvd.rickandmorty.model.PersonagesViewModel

class LocationDetailsFragment : Fragment() {

    private lateinit var binding: FragmentLocationDetailsBinding
    private lateinit var location: Location
    private lateinit var adapter: PersonageAdapter
    private val personagesViewModel: PersonagesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        location = requireArguments().getSerializable(BUNDLE_ARG_LOCATION) as? Location
            ?: throw IllegalArgumentException("you MUST pass Location as argument")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationDetailsBinding.inflate(inflater, container, false)
        adapter = PersonageAdapter(personagesViewModel, location.residentsUrls)
        adapter.onItemClickListener = AbstractAdapter.OnItemClickListener {
            val bundle = Bundle().apply { putSerializable(BUNDLE_ARG_PERSONAGE, it) }
            parentFragmentManager.setFragmentResult(REQUEST_KEY_OPEN_PERSONAGE, bundle)
        }
        binding.recycler.adapter = adapter
        binding.location = location
        binding.executePendingBindings()
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(location: Location) =
            LocationDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(BUNDLE_ARG_LOCATION, location)
                }
            }

        @JvmStatic
        fun newInstance(bundle: Bundle) =
            LocationDetailsFragment().apply {
                arguments = bundle
            }
    }
}