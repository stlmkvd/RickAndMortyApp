package com.stlmkvd.rickandmorty.model

import com.stlmkvd.rickandmorty.Repository
import com.stlmkvd.rickandmorty.data.Episode

class EpisodesViewModel : AbstractRickAndMortyVM<Episode>() {

    override fun loadPage(page: Int): List<Episode> {
        return Repository.getInstance().getEpisodesPagedSync(page)
    }

    override fun loadItemByUrl(url: String): Episode? {
        return Repository.getInstance().getEpisodeByUrlSync(url)
    }
}