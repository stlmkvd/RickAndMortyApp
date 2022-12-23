package com.stlmkvd.rickandmorty.fragments.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.stlmkvd.rickandmorty.data.Personage
import com.stlmkvd.rickandmorty.databinding.FragmentPersonageDetailsBinding
import com.stlmkvd.rickandmorty.fragments.list.EpisodesListFragment
import com.stlmkvd.rickandmorty.model.PersonagesViewModel

private const val TAG = "PersonagesDetailsFragment"

class PersonageDetailsFragment : BaseDetailsFragment<Personage>() {

    private lateinit var binding: FragmentPersonageDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonageDetailsBinding.inflate(inflater, container, false)
        activityViewModels<PersonagesViewModel>().value.loadImage(
            item.imageUrl,
            item.imageFileName
        ) {
            binding.avatar.setImageBitmap(it)
        }
        binding.personage = item
        binding.executePendingBindings()
        childFragmentManager.beginTransaction().replace(
            binding.containerList.id,
            EpisodesListFragment.LinearEpisodesListFragment.newInstance(item.episodeUrls as ArrayList<String>)
        ).commit()
        return binding.root
    }

    companion object Factory {
        fun createInstance(bundle: Bundle): PersonageDetailsFragment =
            PersonageDetailsFragment().apply {
                arguments = bundle
            }
    }
}