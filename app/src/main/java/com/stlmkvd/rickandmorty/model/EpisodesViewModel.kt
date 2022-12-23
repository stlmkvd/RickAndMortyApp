package com.stlmkvd.rickandmorty.model

import com.stlmkvd.rickandmorty.Repository
import com.stlmkvd.rickandmorty.data.Episode

class EpisodesViewModel : BaseViewModel<Episode>() {

    override suspend fun loadItemsPagedSync(pageNum: Int): List<Episode>? {
        return Repository.getInstance().getEpisodesPagedSync(pageNum)
    }

    override suspend fun loadItemByUrl(url: String): Episode? {
        return Repository.getInstance().getEpisodeByUrlSync(url)
    }
}