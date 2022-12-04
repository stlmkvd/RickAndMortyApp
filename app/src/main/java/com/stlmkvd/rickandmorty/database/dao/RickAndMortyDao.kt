package com.stlmkvd.rickandmorty.database.dao

import androidx.room.*
import com.stlmkvd.rickandmorty.data.Personage

@Dao
interface RickAndMortyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(personages: List<Personage>)

    @Delete
    fun delete(personage: Personage)

    @Query("SELECT * FROM Personage")
    fun getAll(): List<Personage>
}