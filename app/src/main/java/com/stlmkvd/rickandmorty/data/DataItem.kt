package com.stlmkvd.rickandmorty.data

interface DataItem : java.io.Serializable {

    interface FilterSelection<I : DataItem> : java.io.Serializable {
        fun matches(item: I): Boolean
    }
}