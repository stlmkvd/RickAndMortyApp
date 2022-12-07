package com.stlmkvd.rickandmorty.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.net.URL
import java.util.*

@Entity
data class Location(
    @PrimaryKey val id: Int,
    val name: String?,
    val type: String?,
    val dimension: String?,
    @SerializedName("residents") val residentsUrls: List<String>,
    val url: String?,
    val created: Date
) : DataItem {


    data class Info(val name: String?, val url: String?) : java.io.Serializable
}