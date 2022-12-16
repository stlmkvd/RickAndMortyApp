package com.stlmkvd.rickandmorty.fragments.overviews

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.stlmkvd.rickandmorty.R
import com.stlmkvd.rickandmorty.adapters.recycler.AbstractAdapter
import com.stlmkvd.rickandmorty.adapters.recycler.LocationsAdapter
import com.stlmkvd.rickandmorty.data.Location
import com.stlmkvd.rickandmorty.databinding.FragmentItemsListBinding
import com.stlmkvd.rickandmorty.fragments.filters.ARG_SELECTION
import com.stlmkvd.rickandmorty.fragments.filters.LocationsFiltersFragment
import com.stlmkvd.rickandmorty.fragments.filters.SUBMIT_SELECTIONS_REQUEST_KEY
import com.stlmkvd.rickandmorty.model.AbstractRickAndMortyVM
import com.stlmkvd.rickandmorty.model.LocationsViewModel
import com.stlmkvd.rickandmorty.setMenuProvider

class LocationsOverviewFragment : Fragment(), AbstractRickAndMortyVM.OnRefreshCompleteCallback {

    private lateinit var viewModel: LocationsViewModel
    private lateinit var binding: FragmentItemsListBinding
    private lateinit var adapter: LocationsAdapter
    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.toolbar_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            binding.slidingLayout.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
            return true
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(LocationsViewModel::class.java)
        binding = FragmentItemsListBinding.inflate(inflater, container, false)
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = LocationsAdapter(viewModel)
        binding.recycler.adapter = adapter
        adapter.onItemClickListener = AbstractAdapter.OnItemClickListener {
            Toast.makeText(requireContext(), "location ${it.id} clicked", Toast.LENGTH_SHORT).show()
        }
        binding.slidingLayout.apply {
            isTouchEnabled = false
            panelState = SlidingUpPanelLayout.PanelState.HIDDEN
        }
        requireActivity().setMenuProvider(menuProvider, viewLifecycleOwner)
        childFragmentManager.beginTransaction()
            .replace(R.id.container_filters, LocationsFiltersFragment::class.java, null).commit()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                viewModel.refresh()
            }
        }
        viewModel.registerOnRefreshCompleteCallback(this)
    }

    override fun onStart() {
        super.onStart()
        childFragmentManager.setFragmentResultListener(
            SUBMIT_SELECTIONS_REQUEST_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            val filters =
                bundle.getSerializable(ARG_SELECTION) as? Location.LocationsFilterSelection
            viewModel.submitFilters(filters)
            binding.slidingLayout.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.removeOnRefreshCompleteCallback(this)
    }

    override fun onRefreshComplete() {
        binding.swipeRefreshLayout.isRefreshing = false
    }
}