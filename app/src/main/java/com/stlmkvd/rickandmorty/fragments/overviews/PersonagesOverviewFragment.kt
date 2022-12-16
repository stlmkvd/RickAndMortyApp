package com.stlmkvd.rickandmorty.fragments.overviews

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.stlmkvd.rickandmorty.R
import com.stlmkvd.rickandmorty.REQUEST_KEY_OPEN_PERSONAGE
import com.stlmkvd.rickandmorty.adapters.recycler.AbstractAdapter
import com.stlmkvd.rickandmorty.adapters.recycler.PersonageAdapter
import com.stlmkvd.rickandmorty.data.Personage
import com.stlmkvd.rickandmorty.databinding.FragmentItemsListBinding
import com.stlmkvd.rickandmorty.fragments.filters.ARG_SELECTION
import com.stlmkvd.rickandmorty.fragments.filters.PersonagesFiltersFragment
import com.stlmkvd.rickandmorty.fragments.filters.SUBMIT_SELECTIONS_REQUEST_KEY
import com.stlmkvd.rickandmorty.model.AbstractRickAndMortyVM
import com.stlmkvd.rickandmorty.model.PersonagesViewModel
import com.stlmkvd.rickandmorty.setMenuProvider

private const val TAG = "PersonagesFragment"

class PersonagesOverviewFragment : Fragment(), AbstractRickAndMortyVM.OnRefreshCompleteCallback {

    private lateinit var viewModel: PersonagesViewModel
    private lateinit var binding: FragmentItemsListBinding
    private lateinit var adapter: PersonageAdapter
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
        viewModel = ViewModelProvider(this).get(PersonagesViewModel::class.java)
        binding = FragmentItemsListBinding.inflate(inflater, container, false)
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = PersonageAdapter(viewModel)
        binding.recycler.adapter = adapter
        binding.slidingLayout.apply {
            isTouchEnabled = false
            panelState = SlidingUpPanelLayout.PanelState.HIDDEN
        }
        requireActivity().setMenuProvider(menuProvider, viewLifecycleOwner)
        childFragmentManager.beginTransaction()
            .replace(R.id.container_filters, PersonagesFiltersFragment::class.java, null).commit()
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
        adapter.onItemClickListener = AbstractAdapter.OnItemClickListener {
            val bundle = Bundle().apply { putSerializable("personage", it) }
            parentFragmentManager.setFragmentResult(REQUEST_KEY_OPEN_PERSONAGE, bundle)
        }

        childFragmentManager.setFragmentResultListener(
            SUBMIT_SELECTIONS_REQUEST_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            val filters =
                bundle.getSerializable(ARG_SELECTION) as? Personage.PersonageFilterSelection
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