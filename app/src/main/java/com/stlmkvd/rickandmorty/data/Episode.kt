package com.stlmkvd.rickandmorty.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.net.URL
import java.util.*

@Entity
data class Episode(
    @PrimaryKey val id: Int,
    val name: String?,
    @SerializedName("air_date") val airDate: String?,
    @SerializedName("episode") val episodeCode: String?,
    @SerializedName("characters") val personageUrls: List<String>,
    val url: String?,
    val created: Date
) : DataItem

