package com.stlmkvd.rickandmorty.fragments.overviews

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener
import com.stlmkvd.rickandmorty.*
import com.stlmkvd.rickandmorty.adapters.recycler.AbstractAdapter
import com.stlmkvd.rickandmorty.adapters.recycler.PersonageAdapter
import com.stlmkvd.rickandmorty.data.Personage
import com.stlmkvd.rickandmorty.databinding.FragmentItemsListBinding
import com.stlmkvd.rickandmorty.fragments.filters.ARG_SELECTION
import com.stlmkvd.rickandmorty.fragments.filters.PersonagesFiltersFragment
import com.stlmkvd.rickandmorty.fragments.filters.REQUEST_KEY_SUBMIT_SELECTIONS
import com.stlmkvd.rickandmorty.model.PersonagesViewModel

private const val TAG = "PersonagesFragment"

class PersonagesOverviewFragment : Fragment() {

    private val viewModel: PersonagesViewModel by activityViewModels()
    private lateinit var binding: FragmentItemsListBinding
    private lateinit var adapter: PersonageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemsListBinding.inflate(inflater, container, false)
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = PersonageAdapter(viewModel)
        binding.recycler.adapter = adapter
        binding.slidingLayout.apply {
            setFadeOnClickListener {
                panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            }
        }
        childFragmentManager.beginTransaction()
            .replace(R.id.container_filters, PersonagesFiltersFragment::class.java, null).commit()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                BitmapProvider.clearCache()
                viewModel.refresh()
                adapter.refresh()
                this.isRefreshing = false //TODO replace with callback
            }
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.onItemClickListener = AbstractAdapter.OnItemClickListener {
            val bundle = Bundle().apply { putSerializable(BUNDLE_ARG_PERSONAGE, it) }
            parentFragmentManager.setFragmentResult(REQUEST_KEY_OPEN_PERSONAGE, bundle)
        }

        childFragmentManager.setFragmentResultListener(
            REQUEST_KEY_SUBMIT_SELECTIONS,
            viewLifecycleOwner
        ) { _, bundle ->
            val filter =
                bundle.getSerializable(ARG_SELECTION) as? Personage.PersonageFilterSelection
            adapter.filter = filter
            binding.slidingLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }
    }
}