package com.stlmkvd.rickandmorty.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.net.URL
import java.util.Date

@Entity
data class Personage(
    @PrimaryKey val id: Int,
    var name: String?,
    var status: String?,
    var species: String?,
    var type: String?,
    var gender: String?,
    var origin: Location.Info?,
    var location: Location.Info?,
    @SerializedName("image") var imageUrl: String,
    @SerializedName("episode") var episodeUrls: List<String>,
    var url: String,
    var created: Date
) : DataItem
