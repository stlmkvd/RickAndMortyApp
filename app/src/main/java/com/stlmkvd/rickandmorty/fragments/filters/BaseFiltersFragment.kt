package com.stlmkvd.rickandmorty.fragments.filters

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.stlmkvd.rickandmorty.data.DataItem
import com.stlmkvd.rickandmorty.fragments.overviews.REQUEST_KEY_CLOSE_FILTERS_PANEL


abstract class BaseFiltersFragment<I : DataItem> : Fragment() {

    protected abstract fun submitSelections()

    protected abstract fun clearSelections()

    protected fun requestClosePanel() {
        parentFragmentManager.setFragmentResult(REQUEST_KEY_CLOSE_FILTERS_PANEL, Bundle.EMPTY)
    }
}