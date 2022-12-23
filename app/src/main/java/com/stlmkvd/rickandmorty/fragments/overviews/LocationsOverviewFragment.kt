package com.stlmkvd.rickandmorty.fragments.overviews

import com.stlmkvd.rickandmorty.data.Location
import com.stlmkvd.rickandmorty.fragments.filters.BaseFiltersFragment
import com.stlmkvd.rickandmorty.fragments.filters.LocationsFiltersFragment
import com.stlmkvd.rickandmorty.fragments.list.BaseListFragment
import com.stlmkvd.rickandmorty.fragments.list.LocationsListFragment

class LocationsOverviewFragment : BaseOverviewFragment<Location>() {

    override fun createFiltersFragment(): BaseFiltersFragment<Location> {
        return LocationsFiltersFragment()
    }

    override fun createListFragment(): BaseListFragment<Location> {
        return LocationsListFragment()
    }
}