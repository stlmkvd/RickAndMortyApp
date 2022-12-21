package com.stlmkvd.rickandmorty.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.net.URL
import java.util.*

@Entity
data class Location(
    @PrimaryKey val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    @SerializedName("residents") val residentsUrls: List<String>,
    override val url: String,
    val created: Date
) : DataItem {


    class LocationsFilterSelection(
        val name: String,
        val type: String,
        val dimension: String
    ) : DataItem.FilterSelection<Location> {

        override fun matches(item: Location): Boolean {
            if (name.isNotBlank() && !item.name.contains(name, true)) return false
            if (type.isNotBlank() && !item.type.contains(type, true)) return false
            if (dimension.isNotBlank() && !item.dimension.contains(dimension, true)) return false
            return true
        }
    }

    data class Info(val name: String, val url: String) : java.io.Serializable
}