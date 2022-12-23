package com.stlmkvd.rickandmorty.fragments.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stlmkvd.rickandmorty.REQUEST_KEY_OPEN_DETAILS
import com.stlmkvd.rickandmorty.adapters.recycler.BaseAdapter
import com.stlmkvd.rickandmorty.data.DataItem
import com.stlmkvd.rickandmorty.databinding.RecyclerListFragmentBinding
import com.stlmkvd.rickandmorty.fragments.overviews.REQUEST_KEY_REFRESH_ENDED
import com.stlmkvd.rickandmorty.fragments.overviews.REQUEST_KEY_REFRESH_RECYCLER

const val REQUEST_KEY_SUBMIT_FILTERS = "SUBMIT_FILTERS"

abstract class BaseListFragment<I : DataItem> : Fragment() {

    protected var urls: List<String>? = null
    protected lateinit var adapter: com.stlmkvd.rickandmorty.adapters.recycler.BaseAdapter<I>
    protected lateinit var binding: RecyclerListFragmentBinding

    abstract fun createAdapter(): com.stlmkvd.rickandmorty.adapters.recycler.BaseAdapter<I>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        urls = arguments?.getStringArrayList(DataItem.BUNDLE_ARG_URL_LIST)
        adapter = createAdapter()
        adapter.urls = urls
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecyclerListFragmentBinding.inflate(inflater, container, false)
        adapter.apply {
            onItemClickListener =
                BaseAdapter.OnItemClickListener {
                    val bundle = Bundle().apply { putSerializable(DataItem.BUNDLE_ARG, it) }
                    parentFragmentManager.setFragmentResult(REQUEST_KEY_OPEN_DETAILS, bundle)
                }
        }
        binding.recycler.adapter = adapter

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        parentFragmentManager.setFragmentResultListener(
            REQUEST_KEY_REFRESH_RECYCLER,
            viewLifecycleOwner
        ) { _, _ ->
            adapter.refresh()
        }

        parentFragmentManager.setFragmentResultListener(
            REQUEST_KEY_SUBMIT_FILTERS,
            viewLifecycleOwner
        ) { _, bundle ->
            adapter.filter =
                bundle.getSerializable(DataItem.FilterSelection.BUNDLE_ARG) as? DataItem.FilterSelection<I>
        }

        adapter.isRefreshing.observe(viewLifecycleOwner) { isRefreshing ->

            if (isRefreshing) {
                binding.progressBar.visibility = View.VISIBLE
                binding.hintNothingFound.visibility = View.GONE
            }
            else {
                binding.progressBar.visibility = View.GONE
                parentFragmentManager.setFragmentResult(REQUEST_KEY_REFRESH_ENDED, Bundle.EMPTY)
            }
        }

        adapter.nothingFound.observe(viewLifecycleOwner) { nothingFound ->
            binding.hintNothingFound.visibility = if (nothingFound) View.VISIBLE else View.GONE
        }
    }
}