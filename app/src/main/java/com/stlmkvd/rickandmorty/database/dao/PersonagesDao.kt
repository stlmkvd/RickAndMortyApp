package com.stlmkvd.rickandmorty.database.dao

import androidx.room.*
import com.stlmkvd.rickandmorty.data.Personage

@Dao
interface PersonagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(personages: List<Personage>)

    @Query("SELECT * FROM Personage WHERE id IN (:ids)")
    fun getPersonagesByIds(ids: List<Int>): List<Personage>

    @Query("SELECT * FROM Personage WHERE id BETWEEN (:page * 20 - 19) AND (:page * 20)")
    fun getPersonagesPaged(page: Int): List<Personage>?

    @Query("SELECT COUNT(id) FROM Personage")
    fun getPersonageCount(): Int
}