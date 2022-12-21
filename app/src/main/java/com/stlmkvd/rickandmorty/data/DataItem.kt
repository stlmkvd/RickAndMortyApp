package com.stlmkvd.rickandmorty.data

interface DataItem : java.io.Serializable {

    val url: String

    interface FilterSelection<I : DataItem> : java.io.Serializable {

        fun matches(item: I): Boolean
    }
}