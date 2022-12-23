package com.stlmkvd.rickandmorty.fragments.overviews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.stlmkvd.rickandmorty.REQUEST_KEY_OPEN_DETAILS
import com.stlmkvd.rickandmorty.data.DataItem
import com.stlmkvd.rickandmorty.databinding.FragmentItemsOverviewBinding
import com.stlmkvd.rickandmorty.fragments.filters.BaseFiltersFragment
import com.stlmkvd.rickandmorty.fragments.list.BaseListFragment

private const val TAG = "PersonagesFragment"

const val REQUEST_KEY_REFRESH_RECYCLER = "REQUEST_KEY_REFRESH"
const val REQUEST_KEY_CLEAR_FILTERS = "REQUEST_KEY_CLEAR_FILTERS"
const val REQUEST_KEY_CLOSE_FILTERS_PANEL = "REQUEST_KEY_CLOSE_FILTERS_PANEL"
const val REQUEST_KEY_REFRESH_ENDED = "REQUEST_KEY_REFRESH_ENDED"

abstract class BaseOverviewFragment<I : DataItem> : Fragment() {

    private lateinit var binding: FragmentItemsOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemsOverviewBinding.inflate(inflater, container, false)
        binding.slidingLayout.apply {
            setFadeOnClickListener {
                panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            }
        }
        childFragmentManager.beginTransaction()
            .replace(binding.containerFilters.id, createFiltersFragment())
            .replace(binding.containerList.id, createListFragment())
            .commit()

        return binding.root
    }

    abstract fun createFiltersFragment(): BaseFiltersFragment<I>

    abstract fun createListFragment(): BaseListFragment<I>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                childFragmentManager.setFragmentResult(REQUEST_KEY_REFRESH_RECYCLER, Bundle.EMPTY)
                childFragmentManager.setFragmentResult(REQUEST_KEY_CLEAR_FILTERS, Bundle.EMPTY)
            }
        }

        childFragmentManager.setFragmentResultListener(
            REQUEST_KEY_OPEN_DETAILS,
            viewLifecycleOwner
        ) { _, bundle ->
            parentFragmentManager.setFragmentResult(REQUEST_KEY_OPEN_DETAILS, bundle)
            Log.d("hgfj", "parentFragmentManager.setFragmentResult(REQUEST_KEY_OPEN_DETAILS, bundle)")
        }

        childFragmentManager.setFragmentResultListener(
            REQUEST_KEY_CLOSE_FILTERS_PANEL,
            viewLifecycleOwner
        ) {
            _, _ ->
            binding.slidingLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }

        childFragmentManager.setFragmentResultListener(
            REQUEST_KEY_REFRESH_ENDED,
            viewLifecycleOwner
        ) {
                _, _ ->
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }
}