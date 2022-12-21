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

private const val TAG = "Repository"
private const val FOLDER_IMAGES = "images"

class Repository private constructor(context: Context) {

    private val imagesDir = context.applicationContext.getDir(FOLDER_IMAGES, Context.MODE_PRIVATE)

    private val db =
        Room.databaseBuilder(context, AppDatabase::class.java, "items_database").build()
    private val rickAndMortyService by lazy { RickAndMortyApi.service }

    fun getPersonagesPagedSync(page: Int): List<Personage> {
        
        var personages: List<Personage>? = null
        try {
            personages = rickAndMortyService.getPersonagesPage(page).execute().body()?.personages
        } catch (e: java.lang.Exception) {

        }

        if (personages != null) db.personagesDao().insertAll(personages)
        else personages = db.personagesDao().getPersonagesPaged(page)

        return personages ?: emptyList()
    }

    fun getPersonageByUrlSync(url: String): Personage? {
        var personage: Personage? = null
        try {
            personage = rickAndMortyService.getPersonageByUrl(url)
        }
        catch (e: java.lang.Exception) { }
        
        if (personage == null) personage = db.personagesDao().getPersonageByUrl(url)
        return personage
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

    fun getLocationByUrlSync(url: String): Location? {
        var location: Location? = null
        try {
            location = rickAndMortyService.getLocationByUrl(url)
        }
        catch (e: java.lang.Exception) {
            Log.e(TAG, e?.localizedMessage.toString())
        }

        if (location == null) location = db.locationsDao().getLocationByUrl(url)
        return location
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

    fun getEpisodeByUrlSync(url: String): Episode? {
        var episode: Episode? = null
        try {
            episode = rickAndMortyService.getEpisodeByUrl(url)
        }
        catch (e: java.lang.Exception) { }

        if (episode == null) episode = db.episodesDao().getEpisodeByUrl(url)
        return episode
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