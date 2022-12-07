package com.stlmkvd.rickandmorty

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.Room
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

    fun getPersonagesPagedSync(page: Int): List<Personage> {
        var personages: List<Personage>?
        try {
            personages = rickAndMortyService.getPersonagesPage(page).execute().body()?.personages
            personages?.let { db.rickAndMortyDao().insertAll(it) }
        } catch (e: IOException) {
            personages = db.rickAndMortyDao().getPersonagesPaged(page)
        }
        return personages ?: emptyList()
    }

    fun getPersonagesByIdsSync(ids: List<Int>): List<Personage> {
        val personages: List<Personage>? =
            rickAndMortyService.getPersonagesByIds(ids.joinToString()).execute().body()
        return if (personages != null) {
            db.rickAndMortyDao().insertAll(personages)
            personages
        } else listOf()
    }

    fun getLocationsPagedSync(page: Int? = null): List<Location> {
        return rickAndMortyService.getLocationsPage(page).execute().body()?.locations ?: listOf()
    }

    fun getLocationsByIdsSync(vararg ids: Int): List<Location> {
        return rickAndMortyService.getLocationsByIds(ids.joinToString()).execute().body()
            ?: listOf()
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