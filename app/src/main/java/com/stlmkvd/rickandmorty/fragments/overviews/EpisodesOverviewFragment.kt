package com.stlmkvd.rickandmorty.fragments.overviews

import com.stlmkvd.rickandmorty.data.Episode
import com.stlmkvd.rickandmorty.fragments.filters.BaseFiltersFragment
import com.stlmkvd.rickandmorty.fragments.filters.EpisodesFiltersFragment
import com.stlmkvd.rickandmorty.fragments.list.BaseListFragment
import com.stlmkvd.rickandmorty.fragments.list.EpisodesListFragment

class EpisodesOverviewFragment : BaseOverviewFragment<Episode>() {

    override fun createFiltersFragment(): BaseFiltersFragment<Episode> {
        return EpisodesFiltersFragment()
    }

    override fun createListFragment(): BaseListFragment<Episode> {
        return EpisodesListFragment()
    }
}