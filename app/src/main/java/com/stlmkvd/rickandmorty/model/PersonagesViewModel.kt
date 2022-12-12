package com.stlmkvd.rickandmorty.model

import com.stlmkvd.rickandmorty.Repository
import com.stlmkvd.rickandmorty.data.Personage

class PersonagesViewModel : AbstractVM<Personage>() {

    override fun loadPage(page: Int): List<Personage> {
        return Repository.getInstance().getPersonagesPagedSync(page)
    }

    override fun loadItemsByIds(ids: List<Int>): List<Personage> {
        return Repository.getInstance().getPersonagesByIdsSync(ids)
    }
}