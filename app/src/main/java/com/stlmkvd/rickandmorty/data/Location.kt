package com.stlmkvd.rickandmorty.data

import com.google.gson.annotations.SerializedName
import java.net.URL
import java.util.Date

data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    @SerializedName("residents") val residentsUrls: List<String>,
    val url: String,
    val created: Date
) {
    data class Info(val name: String?, val url: String?)
}