package com.stlmkvd.rickandmorty.fragments.list

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.stlmkvd.rickandmorty.adapters.recycler.BaseAdapter
import com.stlmkvd.rickandmorty.adapters.recycler.PersonageAdapter
import com.stlmkvd.rickandmorty.data.DataItem
import com.stlmkvd.rickandmorty.data.Personage
import com.stlmkvd.rickandmorty.model.PersonagesViewModel


class PersonagesListFragment : BaseListFragment<Personage>() {

    override fun createAdapter(): BaseAdapter<Personage> {
        return PersonageAdapter(activityViewModels<PersonagesViewModel>().value, urls)
    }

    companion object {

        fun newInstance(urls: ArrayList<String>? = null) =
            PersonagesListFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(DataItem.BUNDLE_ARG_URL_LIST, urls)
                }
            }

        fun newInstance(bundle: Bundle) = PersonagesListFragment().apply {
            arguments = bundle
        }
    }
}