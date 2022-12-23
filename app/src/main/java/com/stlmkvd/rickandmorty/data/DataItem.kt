package com.stlmkvd.rickandmorty.data

interface DataItem : java.io.Serializable {

    val url: String

    interface FilterSelection<I : DataItem> : java.io.Serializable {

        fun matches(item: I): Boolean

        companion object {
            const val BUNDLE_ARG = "FILTER_SELECTION_BUNDLE_ARG"
        }

    }

    companion object {
        const val BUNDLE_ARG = "DATA_ITEM_BUNDLE_ARG"
        const val BUNDLE_ARG_URL_LIST = "DATA_ITEM_BUNDLE_ARG_URL_LIST"
    }
}