package com.stlmkvd.rickandmorty.data

import com.google.gson.annotations.SerializedName
import java.net.URL
import java.util.*

data class Episode(
    val id: Int,
    val name: String?,
    @SerializedName("air_date") val airDate: String?,
    @SerializedName("episode") val episodeCode: String?,
    @SerializedName("characters") val personageUrls: List<String>,
    val url: String?,
    val created: Date
) : DataItem

