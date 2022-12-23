package com.stlmkvd.rickandmorty.fragments.overviews

import com.stlmkvd.rickandmorty.data.Personage
import com.stlmkvd.rickandmorty.fragments.filters.BaseFiltersFragment
import com.stlmkvd.rickandmorty.fragments.filters.PersonagesFiltersFragment
import com.stlmkvd.rickandmorty.fragments.list.BaseListFragment
import com.stlmkvd.rickandmorty.fragments.list.PersonagesListFragment

private const val TAG = "PersonagesFragment"

class PersonagesOverviewFragment : BaseOverviewFragment<Personage>() {

    override fun createFiltersFragment(): BaseFiltersFragment<Personage> {
        return PersonagesFiltersFragment()
    }

    override fun createListFragment(): BaseListFragment<Personage> {
        return PersonagesListFragment()
    }
}