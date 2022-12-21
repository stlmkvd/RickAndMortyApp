package com.stlmkvd.rickandmorty.model

import com.stlmkvd.rickandmorty.Repository
import com.stlmkvd.rickandmorty.data.Personage

class PersonagesViewModel : AbstractRickAndMortyVM<Personage>() {

    override fun loadPage(page: Int): List<Personage> {
        return Repository.getInstance().getPersonagesPagedSync(page)
    }

    override fun loadItemByUrl(url: String): Personage? {
        return Repository.getInstance().getPersonageByUrlSync(url)
    }
}