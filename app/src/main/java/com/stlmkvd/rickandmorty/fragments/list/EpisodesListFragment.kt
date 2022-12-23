package com.stlmkvd.rickandmorty.fragments.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.stlmkvd.rickandmorty.adapters.recycler.BaseAdapter
import com.stlmkvd.rickandmorty.adapters.recycler.EpisodesAdapter
import com.stlmkvd.rickandmorty.data.DataItem
import com.stlmkvd.rickandmorty.data.Episode
import com.stlmkvd.rickandmorty.model.EpisodesViewModel

open class EpisodesListFragment : BaseListFragment<Episode>() {

    override fun createAdapter(): BaseAdapter<Episode> {
        return EpisodesAdapter(activityViewModels<EpisodesViewModel>().value, urls)
    }


    companion object {

        fun newInstance(urls: ArrayList<String>? = null) = EpisodesListFragment().apply {
            arguments = Bundle().apply {
                putStringArrayList(DataItem.BUNDLE_ARG_URL_LIST, urls)
            }
        }

        fun newInstance(bundle: Bundle) = EpisodesListFragment().apply {
            arguments = bundle
        }
    }


    class LinearEpisodesListFragment : EpisodesListFragment() {
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        }

        companion object {

            fun newInstance(urls: ArrayList<String>? = null) = LinearEpisodesListFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(DataItem.BUNDLE_ARG_URL_LIST, urls)
                }
            }

            fun newInstance(bundle: Bundle) = LinearEpisodesListFragment().apply {
                arguments = bundle
            }
        }
    }
}