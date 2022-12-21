package com.stlmkvd.rickandmorty.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
data class Episode(
    @PrimaryKey val id: Int,
    val name: String,
    @SerializedName("air_date") val airDate: String,
    @SerializedName("episode") val episodeCode: String,
    @SerializedName("characters") val personageUrls: List<String>,
    override val url: String,
    val created: Date
) : DataItem {

    class EpisodesFilterSelection(
        private val name: String,
        private val episodeCode: String
    ) : DataItem.FilterSelection<Episode> {

        override fun matches(item: Episode): Boolean {
            if (name.isNotBlank() && !item.name.contains(name, true)) return false
            if (episodeCode.isNotBlank() && !item.episodeCode.contains(
                    episodeCode,
                    true
                )
            ) return false
            return true
        }
    }

}

