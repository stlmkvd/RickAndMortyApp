package com.stlmkvd.rickandmorty.network

import com.google.gson.GsonBuilder
import com.stlmkvd.rickandmorty.data.Episode
import com.stlmkvd.rickandmorty.data.Location
import com.stlmkvd.rickandmorty.data.Personage
import com.stlmkvd.rickandmorty.network.page.EpisodesPage
import com.stlmkvd.rickandmorty.network.page.LocationsPage
import com.stlmkvd.rickandmorty.network.page.PersonagePage
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val TAG = "RickAndMortyApi"
private const val BASE_URL = "https://rickandmortyapi.com/api/"

val gson =
    GsonBuilder()
        .serializeNulls()
        .create()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()


interface RickAndMortyService {

    @GET("character/")
    fun getPersonagesPage(@Query("page") page: Int? = null): Call<PersonagePage>

    @GET("character/[{ids}]")
    fun getPersonagesByIds(@Path("ids") ids: String): Call<List<Personage>>

    @GET("location/")
    fun getLocationsPage(@Query("page") page: Int? = null): Call<LocationsPage>

    @GET("location/[{ids}]")
    fun getLocationsByIds(@Path("ids") ids: String): Call<List<Location>>

    @GET("episode/")
    fun getEpisodesPage(@Query("page") page: Int? = null): Call<EpisodesPage>

    @GET("episode/[{ids}]")
    fun getEpisodesByIds(@Path("ids") ids: String): Call<List<Episode>>
}

object RickAndMortyApi {
    val service: RickAndMortyService by lazy {
        retrofit.create(RickAndMortyService::class.java)
    }
}
