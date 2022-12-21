package com.stlmkvd.rickandmorty.fragments.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.stlmkvd.rickandmorty.*
import com.stlmkvd.rickandmorty.adapters.recycler.AbstractAdapter
import com.stlmkvd.rickandmorty.adapters.recycler.PersonageAdapter
import com.stlmkvd.rickandmorty.data.Episode
import com.stlmkvd.rickandmorty.data.Location
import com.stlmkvd.rickandmorty.databinding.FragmentEpisodeDetailsBinding
import com.stlmkvd.rickandmorty.model.PersonagesViewModel

class EpisodeDetailsFragment : Fragment() {

    private lateinit var episode: Episode
    private val personagesViewModel: PersonagesViewModel by activityViewModels()
    private lateinit var binding: FragmentEpisodeDetailsBinding
    private lateinit var adapter: PersonageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        episode = requireArguments().getSerializable(BUNDLE_ARG_EPISODE) as? Episode
            ?: throw java.lang.IllegalArgumentException("you MUST pass Episode as argument")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodeDetailsBinding.inflate(inflater, container, false)
        binding.episode = episode
        binding.executePendingBindings()
        adapter = PersonageAdapter(personagesViewModel, episode.personageUrls)
        adapter.onItemClickListener = AbstractAdapter.OnItemClickListener {
            val bundle = Bundle().apply { putSerializable(BUNDLE_ARG_PERSONAGE, it) }
            parentFragmentManager.setFragmentResult(REQUEST_KEY_OPEN_PERSONAGE, bundle)
        }
        binding.recycler.adapter = adapter
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(episode: Episode) =
            EpisodeDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(BUNDLE_ARG_EPISODE, episode)
                }
            }

        @JvmStatic
        fun newInstance(bundle: Bundle) =
            EpisodeDetailsFragment().apply {
                arguments = bundle
            }
    }
}