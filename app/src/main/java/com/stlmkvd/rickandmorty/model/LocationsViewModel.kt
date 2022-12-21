package com.stlmkvd.rickandmorty.model

import com.stlmkvd.rickandmorty.Repository
import com.stlmkvd.rickandmorty.data.Location

class LocationsViewModel : AbstractRickAndMortyVM<Location>() {

    override fun loadPage(page: Int): List<Location> {
        return Repository.getInstance().getLocationsPagedSync(page)
    }

    override fun loadItemByUrl(url: String): Location? {
        return Repository.getInstance().getLocationByUrlSync(url)
    }
}