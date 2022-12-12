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
import com.stlmkvd.rickandmorty.adapters.recycler.LocationsAdapter
import com.stlmkvd.rickandmorty.databinding.FragmentPersonagesBinding
import com.stlmkvd.rickandmorty.model.LocationsViewModel

class LocationsOverviewFragment : Fragment() {

    private lateinit var viewModel: LocationsViewModel
    private lateinit var binding: FragmentPersonagesBinding
    private lateinit var adapter: LocationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(LocationsViewModel::class.java)
        binding = FragmentPersonagesBinding.inflate(inflater, container, false)
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = LocationsAdapter(viewModel)
        binding.recycler.adapter = adapter
        adapter.onItemClickListener = AbstractAdapter.OnItemClickListener {
            Toast.makeText(requireContext(), "location ${it.id} clicked", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }
}