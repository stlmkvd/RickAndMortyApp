package com.stlmkvd.rickandmorty.model

import com.stlmkvd.rickandmorty.Repository
import com.stlmkvd.rickandmorty.data.Location

class LocationsViewModel : BaseViewModel<Location>() {

    override suspend fun loadItemsPagedSync(pageNum: Int): List<Location>? {
        return Repository.getInstance().getLocationsPagedSync(pageNum)
    }

    override suspend fun loadItemByUrl(url: String): Location? {
        return Repository.getInstance().getLocationByUrlSync(url)
    }
}