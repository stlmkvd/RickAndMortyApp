package com.stlmkvd.rickandmorty.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.stlmkvd.rickandmorty.data.Episode
import com.stlmkvd.rickandmorty.data.Location
import com.stlmkvd.rickandmorty.data.Personage
import com.stlmkvd.rickandmorty.network.page.EpisodesPage
import com.stlmkvd.rickandmorty.network.page.LocationsPage
import com.stlmkvd.rickandmorty.network.page.PersonagePage
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

private const val TAG = "RickAndMortyApi"
private const val BASE_URL = "https://rickandmortyapi.com/api/"

val gson: Gson =
    GsonBuilder()
        .serializeNulls()
        .create()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()


interface RickAndMortyService {

    @GET("character/")
    suspend fun getPersonagesPage(@Query("page") page: Int? = null): PersonagePage?

    @GET
    suspend fun getPersonageByUrl(@Url url: String): Personage?

    @GET("character/")
    suspend fun getPersonagesFiltered(
        @Query("name") name: String,
        @Query("status") status: String,
        @Query("species") species: String,
        @Query("type") type: String,
        @Query("gender") gender: String
    ): List<Personage>?


    @GET("location/")
    suspend fun getLocationsPage(@Query("page") page: Int? = null): LocationsPage?

    @GET
    suspend fun getLocationByUrl(@Url url: String): Location?

    @GET("location/")
    suspend fun getLocationsFiltered(
        @Query("name") name: String,
        @Query("type") type: String,
        @Query("dimension") dimension: String
    ): List<Location>?


    @GET("episode/")
    suspend fun getEpisodesPage(@Query("page") page: Int? = null): EpisodesPage?

    @GET
    suspend fun getEpisodeByUrl(@Url url: String): Episode?

    @GET("episode/")
    suspend fun getEpisodesFiltered(
        @Query("name") name: String,
        @Query("episode") episode: String
    ): List<Episode>?

    @GET
    suspend fun downloadImage(@Url url: String): ResponseBody?
}

object RickAndMortyApi {
    val service: RickAndMortyService by lazy {
        retrofit.create(RickAndMortyService::class.java)
    }
}
