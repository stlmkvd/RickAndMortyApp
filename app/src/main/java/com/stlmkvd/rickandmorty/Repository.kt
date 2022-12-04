package com.stlmkvd.rickandmorty

import android.content.Context
import androidx.room.Room
import com.stlmkvd.rickandmorty.data.Location
import com.stlmkvd.rickandmorty.data.Personage
import com.stlmkvd.rickandmorty.database.AppDatabase
import com.stlmkvd.rickandmorty.network.RickAndMortyApi

private const val BASE_URL = "https://rickandmortyapi.com/api"
private const val TAG = "Repository"

class Repository private constructor(context: Context) {
    private val db = Room.databaseBuilder(context, AppDatabase::class.java, "items_database").build()
    private val rickAndMortyService by lazy { RickAndMortyApi.service }

    fun loadAllPersonagesFromRepository(): List<Personage> {
        return db.rickAndMortyDao().getAll()
    }

    fun getPersonagesPage(page: Int? = null): List<Personage> {
        val personagePage = rickAndMortyService.getPersonagesPage(page).execute().body()
        personagePage?.let { db.rickAndMortyDao().insertAll(it.personages) }
        return personagePage?.personages ?: listOf()
    }

    fun getPersonagesByIds(vararg ids: Int): List<Personage> {
        return rickAndMortyService.getPersonagesByIds(ids.joinToString()).execute().body()
            ?: listOf()
    }

    fun getLocationsPage(page: Int? = null): List<Location> {
        return rickAndMortyService.getLocationsPage(page).execute().body()?.locations ?: listOf()
    }

    fun getLocationsByIds(vararg ids: Int): List<Location> {
        return rickAndMortyService.getLocationsByIds(ids.joinToString()).execute().body()
            ?: listOf()
    }


    companion object {
        private var instance: Repository? = null

        fun init(context: Context) {
            instance = Repository(context)
        }

        fun getInstance(): Repository {
            if (instance == null) throw java.lang.IllegalStateException("Repository not initialized")
            return instance!!
        }
    }
}