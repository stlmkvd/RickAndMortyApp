package com.stlmkvd.rickandmorty.fragments.overviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.stlmkvd.rickandmorty.R
import com.stlmkvd.rickandmorty.REQUEST_KEY_OPEN_PERSONAGE
import com.stlmkvd.rickandmorty.adapters.recycler.AbstractAdapter
import com.stlmkvd.rickandmorty.adapters.recycler.PersonageAdapter
import com.stlmkvd.rickandmorty.databinding.FragmentPersonagesBinding
import com.stlmkvd.rickandmorty.fragments.details.PersonagesDetailsFragment
import com.stlmkvd.rickandmorty.model.PersonagesViewModel

private const val TAG = "PersonagesFragment"

class PersonagesOverviewFragment : Fragment() {

    private lateinit var viewModel: PersonagesViewModel
    private lateinit var binding: FragmentPersonagesBinding
    private lateinit var adapter: PersonageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(PersonagesViewModel::class.java)
        binding = FragmentPersonagesBinding.inflate(inflater, container, false)
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = PersonageAdapter(viewModel)
        binding.recycler.adapter = adapter
        adapter.onItemClickListener = AbstractAdapter.OnItemClickListener {
            val bundle = Bundle().apply { putSerializable("personage", it) }
            parentFragmentManager.setFragmentResult(REQUEST_KEY_OPEN_PERSONAGE, bundle)
        }
        return binding.root
    }
}