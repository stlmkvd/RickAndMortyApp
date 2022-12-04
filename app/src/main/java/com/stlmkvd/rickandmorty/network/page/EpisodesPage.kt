package com.stlmkvd.rickandmorty.network.page

import com.google.gson.annotations.SerializedName
import com.stlmkvd.rickandmorty.data.Episode

data class EpisodesPage(val info: PageInfo, @SerializedName("results") val episodes: List<Episode>) {
}