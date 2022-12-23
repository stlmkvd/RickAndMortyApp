package com.stlmkvd.rickandmorty.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
data class Personage(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Location.Info,
    val location: Location.Info,
    @SerializedName("image") var imageUrl: String,
    @SerializedName("episode") val episodeUrls: List<String>,
    override val url: String,
    val created: Date,
) : DataItem {
    val imageFileName: String
        get() = "personage_$id.jpg"


    data class FilterSelection(
        val name: String,
        val species: String,
        val type: String,
    ) : DataItem.FilterSelection<Personage> {

        val TAG = "PersonageFilterSelection"

        val statuses: MutableSet<String> = HashSet()
        val genders: MutableSet<String> = HashSet()

        override fun matches(item: Personage): Boolean {
            if (name.isNotBlank() && !item.name.contains(name, true)) return false
            if (!statuses.contains(item.status)) return false
            if (species.isNotBlank() && !item.species.contains(species, true)) return false
            if (type.isNotBlank() && !item.type.contains(type, true)) return false
            if (!genders.contains(item.gender)) return false
            return true
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as FilterSelection

            if (name != other.name) return false
            if (species != other.species) return false
            if (type != other.type) return false
            if (TAG != other.TAG) return false
            if (statuses != other.statuses) return false
            if (genders != other.genders) return false

            return true
        }

        override fun hashCode(): Int {
            var result = name.hashCode()
            result = 31 * result + species.hashCode()
            result = 31 * result + type.hashCode()
            result = 31 * result + TAG.hashCode()
            result = 31 * result + statuses.hashCode()
            result = 31 * result + genders.hashCode()
            return result
        }

        companion object {
            const val BUNDLE_ARG = "PERSONAGE_FILTER_SELECTION_BUNDLE_ARG"
        }
    }


    companion object {
        const val STATUS_ALIVE = "Alive"
        const val STATUS_DEAD = "Dead"
        const val STATUS_UNKNOWN = "unknown"
        const val GENDER_MALE = "Male"
        const val GENDER_FEMALE = "Female"
        const val GENDER_GENDERLESS = "Genderless"
        const val GENDER_UNKNOWN = "unknown"

        const val BUNDLE_ARG = "PERSONAGE_BUNDLE_ARG"
    }
}
