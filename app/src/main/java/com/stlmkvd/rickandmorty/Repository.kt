package com.stlmkvd.rickandmorty

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.room.Room
import com.stlmkvd.rickandmorty.data.DataItem
import com.stlmkvd.rickandmorty.data.Episode
import com.stlmkvd.rickandmorty.data.Location
import com.stlmkvd.rickandmorty.data.Personage
import com.stlmkvd.rickandmorty.database.AppDatabase
import com.stlmkvd.rickandmorty.network.RickAndMortyApi
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException

private const val BASE_URL = "https://rickandmortyapi.com/api"
private const val TAG = "Repository"
private const val FOLDER_IMAGES = "images"

class Repository private constructor(context: Context) {

    private val imagesDir = context.applicationContext.getDir(FOLDER_IMAGES, Context.MODE_PRIVATE)

    private val db =
        Room.databaseBuilder(context, AppDatabase::class.java, "items_database").build()
    private val rickAndMortyService by lazy { RickAndMortyApi.service }

    fun getItemsPaged(page: Int, clazz: Class<out DataItem>): List<DataItem> {
        return when(clazz) {
            Personage::class.java -> getPersonagesPagedSync(page)
            Location::class.java -> getLocationsPagedSync(page)
            Episode::class.java -> getEpisodesPagedSync(page)
            else -> throw IllegalArgumentException("no such type supported")
        }
    }

    fun getPersonagesPagedSync(page: Int): List<Personage> {
        Log.d(TAG, "personage page: $page")
        var personages: List<Personage>? = null
        try {
            personages = rickAndMortyService.getPersonagesPage(page).execute().body()?.personages
        } catch (e: IOException) {

        }

        if (personages != null) db.personagesDao().insertAll(personages)
        else personages = db.personagesDao().getPersonagesPaged(page)

        return personages ?: emptyList()
    }

    fun getPersonagesByIdsSync(ids: List<Int>): List<Personage> {
        val personages: List<Personage>? =
            rickAndMortyService.getPersonagesByIds(ids.joinToString()).execute().body()
        return if (personages != null) {
            db.personagesDao().insertAll(personages)
            personages
        } else listOf()
    }

    fun getLocationsPagedSync(page: Int): List<Location> {
        var locations: List<Location>? = null
        try {
            locations = rickAndMortyService.getLocationsPage(page).execute().body()?.locations
        } catch (e: IOException) {

        }

        if (locations != null) {
            db.locationsDao().insertAll(locations)
        } else locations = db.locationsDao().getLocationsPaged(page)

        return locations ?: emptyList()
    }

    fun getLocationsByIdsSync(ids: List<Int>): List<Location> {
        return TODO()
    }

    fun getEpisodesPagedSync(page: Int): List<Episode> {
        var episodes: List<Episode>? = null
        try {
            episodes = rickAndMortyService.getEpisodesPage(page).execute().body()?.episodes
        } catch (e: IOException) {
        }

        if (episodes != null) db.episodesDao().insertAll(episodes)
        else episodes = db.episodesDao().getEpisodesPaged(page)

        return episodes ?: listOf()
    }

    fun getEpisodesByIds(ids: List<Int>): List<Episode> {
        return TODO()
    }

    fun loadImageSync(imageUrl: String, name: String): Bitmap? {
        var bitmap: Bitmap? = try {
            BitmapFactory.decodeStream(
                rickAndMortyService.downloadImage(imageUrl).execute().body()?.byteStream()
            )
        } catch (e: java.lang.Exception) {
            null
        }
        if (bitmap != null) {
            saveImageToStorage(name, bitmap)
        } else bitmap = loadImageFromStorage(name)
        return bitmap
    }

    private fun loadImageFromStorage(name: String): Bitmap? {
        val imageFile = imagesDir.listFiles()?.find { it.name == name } ?: return null
        return BitmapFactory.decodeStream(FileInputStream(imageFile))
    }

    private fun saveImageToStorage(name: String, bitmap: Bitmap) {
        val file = File(imagesDir, name)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        file.writeBytes(baos.toByteArray())
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