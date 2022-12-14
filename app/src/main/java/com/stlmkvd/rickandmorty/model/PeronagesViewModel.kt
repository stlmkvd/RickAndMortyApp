package com.stlmkvd.rickandmorty.model

import com.stlmkvd.rickandmorty.Repository
import com.stlmkvd.rickandmorty.data.Personage

class PersonagesViewModel : BaseViewModel<Personage>() {

    override suspend fun loadItemsPagedSync(pageNum: Int): List<Personage>? {
        return Repository.getInstance().getPersonagesPagedSync(pageNum)
    }

    override suspend fun loadItemByUrl(url: String): Personage? {
        return Repository.getInstance().getPersonageByUrlSync(url)
    }
}