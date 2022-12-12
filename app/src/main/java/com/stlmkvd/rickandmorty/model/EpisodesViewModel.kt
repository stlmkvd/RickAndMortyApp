package com.stlmkvd.rickandmorty.model

import com.stlmkvd.rickandmorty.Repository
import com.stlmkvd.rickandmorty.data.Episode

class EpisodesViewModel : AbstractVM<Episode>() {

    override fun loadPage(page: Int): List<Episode> {
        return Repository.getInstance().getEpisodesPagedSync(page)
    }

    override fun loadItemsByIds(ids: List<Int>): List<Episode> {
        return Repository.getInstance().getEpisodesByIds(ids)
    }
}