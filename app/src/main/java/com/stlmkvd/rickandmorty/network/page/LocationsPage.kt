package com.stlmkvd.rickandmorty.network.page

import com.google.gson.annotations.SerializedName
import com.stlmkvd.rickandmorty.data.Location

data class LocationsPage(val info: PageInfo, @SerializedName("results") val locations: List<Location>)