package com.stlmkvd.rickandmorty.fragments.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.stlmkvd.rickandmorty.BUNDLE_ARG_EPISODE
import com.stlmkvd.rickandmorty.BUNDLE_ARG_PERSONAGE
import com.stlmkvd.rickandmorty.BitmapProvider
import com.stlmkvd.rickandmorty.REQUEST_KEY_OPEN_EPISODE
import com.stlmkvd.rickandmorty.adapters.recycler.AbstractAdapter
import com.stlmkvd.rickandmorty.adapters.recycler.EpisodesAdapter
import com.stlmkvd.rickandmorty.adapters.recycler.PersonageAdapter
import com.stlmkvd.rickandmorty.data.Personage
import com.stlmkvd.rickandmorty.databinding.FragmentPersonageDetailsBinding
import com.stlmkvd.rickandmorty.model.EpisodesViewModel

private const val TAG = "PersonagesDetailsFragment"

class PersonageDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPersonageDetailsBinding
    private val viewModel: EpisodesViewModel by activityViewModels()
    private lateinit var personage: Personage
    private lateinit var adapter: EpisodesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        personage = requireArguments().getSerializable(BUNDLE_ARG_PERSONAGE) as? Personage
                ?: throw IllegalArgumentException("you MUST pass Personage as argument")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonageDetailsBinding.inflate(inflater, container, false)
        BitmapProvider.loadImageAsync(personage.imageUrl, personage.imageFileName) {
            binding.avatar.setImageBitmap(it)
        }
        adapter = EpisodesAdapter(viewModel, personage.episodeUrls)
        binding.recycler.adapter = adapter
        adapter.onItemClickListener = AbstractAdapter.OnItemClickListener {
            val bundle = Bundle().apply { putSerializable(BUNDLE_ARG_EPISODE, it) }
            parentFragmentManager.setFragmentResult(REQUEST_KEY_OPEN_EPISODE, bundle)
        }
        binding.personage = personage
        binding.executePendingBindings()
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(personage: Personage) =
            PersonageDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(BUNDLE_ARG_PERSONAGE, personage)
                }
            }

        @JvmStatic
        fun newInstance(bundle: Bundle) =
            PersonageDetailsFragment().apply {
                arguments = bundle
            }
    }
}