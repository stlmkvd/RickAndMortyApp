package com.stlmkvd.rickandmorty

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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

private const val TAG = "Repository"
private const val FOLDER_IMAGES = "images"

class Repository private constructor(context: Context) {

    private val imagesDir = context.applicationContext.getDir(FOLDER_IMAGES, Context.MODE_PRIVATE)

    private val db =
        Room.databaseBuilder(context, AppDatabase::class.java, "items_database").build()
    private val rickAndMortyService by lazy { RickAndMortyApi.service }

    suspend fun getPersonagesPagedSync(page: Int): List<Personage>? {

        var personages: List<Personage>? =
            try {
                rickAndMortyService.getPersonagesPage(page)?.personages
            } catch (e: java.lang.Exception) {
                null
            }

        if (personages != null) db.personagesDao().insertAll(personages)
        else personages = db.personagesDao().getPersonagesPaged(page)

        return personages
    }

    suspend fun getPersonageByUrlSync(url: String): Personage? {
        var personage: Personage? =
            try {
                rickAndMortyService.getPersonageByUrl(url)
            } catch (e: java.lang.Exception) {
                null
            }

        if (personage == null) personage = db.personagesDao().getPersonageByUrl(url)
        return personage
    }


    suspend fun getLocationsPagedSync(page: Int): List<Location>? {
        var locations: List<Location>? =
            try {
                rickAndMortyService.getLocationsPage(page)?.locations
            } catch (e: java.lang.Exception) {
                null
            }

        if (locations != null) {
            db.locationsDao().insertAll(locations)
        } else locations = db.locationsDao().getLocationsPaged(page)

        return locations
    }

    suspend fun getLocationByUrlSync(url: String): Location? {
        var location: Location? =
            try {
                rickAndMortyService.getLocationByUrl(url)
            } catch (e: java.lang.Exception) {
                null
            }

        if (location == null) location = db.locationsDao().getLocationByUrl(url)
        return location
    }


    suspend fun getEpisodesPagedSync(page: Int): List<Episode>? {
        var episodes: List<Episode>? =
            try {
                rickAndMortyService.getEpisodesPage(page)?.episodes
            } catch (e: java.lang.Exception) {
                null
            }

        if (episodes != null) db.episodesDao().insertAll(episodes)
        else episodes = db.episodesDao().getEpisodesPaged(page)

        return episodes
    }

    suspend fun getEpisodeByUrlSync(url: String): Episode? {
        var episode: Episode? =
            try {
                rickAndMortyService.getEpisodeByUrl(url)
            } catch (e: java.lang.Exception) {
                null
            }

        if (episode == null) episode = db.episodesDao().getEpisodeByUrl(url)
        return episode
    }

    suspend fun loadImageSync(imageUrl: String, name: String): Bitmap? {
        var bitmap: Bitmap? = try {
            BitmapFactory.decodeStream(
                rickAndMortyService.downloadImage(imageUrl)?.byteStream()
            )
        } catch (e: java.lang.Exception) {
            null
        }
        if (bitmap != null) {
            saveImageToStorage(name, bitmap)
        } else bitmap = loadImageFromStorage(name)
        return bitmap
    }

    private suspend fun loadImageFromStorage(name: String): Bitmap? {
        val imageFile = imagesDir.listFiles()?.find { it.name == name } ?: return null
        return BitmapFactory.decodeStream(FileInputStream(imageFile))
    }

    private suspend fun saveImageToStorage(name: String, bitmap: Bitmap) {
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