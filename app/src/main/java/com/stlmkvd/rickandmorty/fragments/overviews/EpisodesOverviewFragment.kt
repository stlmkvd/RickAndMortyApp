package com.stlmkvd.rickandmorty.fragments.overviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.stlmkvd.rickandmorty.adapters.recycler.AbstractAdapter
import com.stlmkvd.rickandmorty.adapters.recycler.EpisodesAdapter
import com.stlmkvd.rickandmorty.databinding.FragmentEpisodesBinding
import com.stlmkvd.rickandmorty.model.EpisodesViewModel

class EpisodesOverviewFragment
    : Fragment() {

    private lateinit var viewModel: EpisodesViewModel
    private lateinit var binding: FragmentEpisodesBinding
    private lateinit var adapter: EpisodesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(EpisodesViewModel::class.java)
        binding = FragmentEpisodesBinding.inflate(inflater, container, false)
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = EpisodesAdapter(viewModel)
        binding.recycler.adapter = adapter
        adapter.onItemClickListener = AbstractAdapter.OnItemClickListener {
            Toast.makeText(requireContext(), "episode ${it.id} clicked", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }
}