package com.stlmkvd.rickandmorty.fragments.overviews

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.stlmkvd.rickandmorty.BUNDLE_ARG_EPISODE
import com.stlmkvd.rickandmorty.R
import com.stlmkvd.rickandmorty.REQUEST_KEY_OPEN_EPISODE
import com.stlmkvd.rickandmorty.adapters.recycler.AbstractAdapter
import com.stlmkvd.rickandmorty.adapters.recycler.EpisodesAdapter
import com.stlmkvd.rickandmorty.data.Episode
import com.stlmkvd.rickandmorty.databinding.FragmentItemsListBinding
import com.stlmkvd.rickandmorty.fragments.filters.ARG_SELECTION
import com.stlmkvd.rickandmorty.fragments.filters.EpisodesFiltersFragment
import com.stlmkvd.rickandmorty.fragments.filters.REQUEST_KEY_SUBMIT_SELECTIONS
import com.stlmkvd.rickandmorty.model.EpisodesViewModel
import com.stlmkvd.rickandmorty.setMenuProvider

class EpisodesOverviewFragment
    : Fragment() {

    private val viewModel: EpisodesViewModel by activityViewModels()
    private lateinit var binding: FragmentItemsListBinding
    private lateinit var adapter: EpisodesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemsListBinding.inflate(inflater, container, false)
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = EpisodesAdapter(viewModel)
        adapter.onItemClickListener = AbstractAdapter.OnItemClickListener {
            val bundle = Bundle().apply { putSerializable(BUNDLE_ARG_EPISODE, it) }
            parentFragmentManager.setFragmentResult(REQUEST_KEY_OPEN_EPISODE, bundle)
        }
        binding.recycler.adapter = adapter
        binding.slidingLayout.apply {
            setFadeOnClickListener {
                panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            }
        }
        childFragmentManager.beginTransaction()
            .replace(R.id.container_filters, EpisodesFiltersFragment::class.java, null).commit()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                viewModel.refresh()
                adapter.refresh()
                this.isRefreshing = false //TODO replace with callback
            }
        }
    }

    override fun onStart() {
        super.onStart()
        childFragmentManager.setFragmentResultListener(
            REQUEST_KEY_SUBMIT_SELECTIONS,
            viewLifecycleOwner
        ) { _, bundle ->
            val filter =
                bundle.getSerializable(ARG_SELECTION) as? Episode.EpisodesFilterSelection
            adapter.filter = filter
            binding.slidingLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        viewModel.removeOnRefreshCompleteCallback(this)
//    }
//
//    override fun onRefreshComplete() {
//        binding.swipeRefreshLayout.isRefreshing = false
//    }
}