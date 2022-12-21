package com.stlmkvd.rickandmorty.database.dao

import androidx.room.*
import com.stlmkvd.rickandmorty.data.Episode
import com.stlmkvd.rickandmorty.data.Location
import com.stlmkvd.rickandmorty.data.Personage

//const val ENTITY_PERSONAGE = "Personage"
//const val ENTITY_LOCATION = "Location"
//const val ENTITY_EPISODE = "Episode"
//
//@Dao
//interface ItemsDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAll(personages: List<Personage>)
//
//    @Query("SELECT * FROM Personage WHERE id IN (:ids)")
//    fun getPersonagesByIds(ids: List<Int>): List<Personage>
//
//    @Query("SELECT * FROM Personage WHERE id BETWEEN (:page * 20 - 19) AND (:page * 20)")
//    fun getPersonagesPaged(page: Int): List<Personage>?
//
//    @Query("SELECT COUNT(id) FROM Personage")
//    fun getPersonageCount(): Int
//}

@Dao
interface PersonagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(personages: List<Personage>)

    @Query("SELECT * FROM Personage WHERE id IN (:ids)")
    fun getPersonagesByIds(ids: List<Int>): List<Personage>

    @Query("SELECT * FROM Personage WHERE id BETWEEN (:page * 20 - 19) AND (:page * 20)")
    fun getPersonagesPaged(page: Int): List<Personage>?

    @Query("SELECT * FROM Personage WHERE url = :url")
    fun getPersonageByUrl(url: String): Personage?

    @Query("SELECT COUNT(id) FROM Personage")
    fun getPersonageCount(): Int
}

@Dao
interface LocationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(locations: List<Location>)

    @Query("SELECT * FROM Location WHERE id IN (:ids)")
    fun getLocationsByIds(ids: List<Int>): List<Location>

    @Query("SELECT * FROM Location WHERE id BETWEEN (:page * 20 - 19) AND (:page * 20)")
    fun getLocationsPaged(page: Int): List<Location>?

    @Query("SELECT * FROM Location WHERE url = :url")
    fun getLocationByUrl(url: String): Location?

    @Query("SELECT COUNT(id) FROM Location")
    fun getLocationsCount(): Int
}

@Dao
interface EpisodesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(locations: List<Episode>)

    @Query("SELECT * FROM Episode WHERE id IN (:ids)")
    fun getEpisodesByIds(ids: List<Int>): List<Episode>

    @Query("SELECT * FROM Episode WHERE id BETWEEN (:page * 20 - 19) AND (:page * 20)")
    fun getEpisodesPaged(page: Int): List<Episode>?

    @Query("SELECT * FROM Episode WHERE url = :url")
    fun getEpisodeByUrl(url: String): Episode?

    @Query("SELECT COUNT(id) FROM Episode")
    fun getEpisodesCount(): Int
}