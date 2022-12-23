package com.stlmkvd.rickandmorty.fragments.list

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.stlmkvd.rickandmorty.adapters.recycler.BaseAdapter
import com.stlmkvd.rickandmorty.adapters.recycler.LocationsAdapter
import com.stlmkvd.rickandmorty.data.DataItem
import com.stlmkvd.rickandmorty.data.Location
import com.stlmkvd.rickandmorty.model.LocationsViewModel

class LocationsListFragment : BaseListFragment<Location>() {

    override fun createAdapter(): BaseAdapter<Location> {
        return LocationsAdapter(activityViewModels<LocationsViewModel>().value, urls)
    }

    companion object {

        fun newInstance(urls: ArrayList<String>? = null) =
            LocationsListFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(DataItem.BUNDLE_ARG_URL_LIST, urls)
                }
            }

        fun newInstance(bundle: Bundle) = LocationsListFragment().apply {
            arguments = bundle
        }
    }
}