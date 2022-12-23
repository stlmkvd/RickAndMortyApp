package com.stlmkvd.rickandmorty.fragments.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.stlmkvd.rickandmorty.REQUEST_KEY_OPEN_DETAILS
import com.stlmkvd.rickandmorty.data.DataItem

abstract class BaseDetailsFragment<I : DataItem> : Fragment() {

    protected lateinit var item: I

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = requireArguments().getSerializable(DataItem.BUNDLE_ARG) as? I
            ?: throw IllegalArgumentException("invalid fragment dataItem argument")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.setFragmentResultListener(REQUEST_KEY_OPEN_DETAILS, viewLifecycleOwner) {
                _, bundle ->
            parentFragmentManager.setFragmentResult(REQUEST_KEY_OPEN_DETAILS, bundle)
        }
    }
}