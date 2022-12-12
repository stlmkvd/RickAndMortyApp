package com.stlmkvd.rickandmorty.model

import com.stlmkvd.rickandmorty.Repository
import com.stlmkvd.rickandmorty.data.Location

class LocationsViewModel : AbstractVM<Location>() {

    override fun loadPage(page: Int): List<Location> {
        return Repository.getInstance().getLocationsPagedSync(page)
    }

    override fun loadItemsByIds(ids: List<Int>): List<Location> {
        return Repository.getInstance().getLocationsByIdsSync(ids)
    }
}