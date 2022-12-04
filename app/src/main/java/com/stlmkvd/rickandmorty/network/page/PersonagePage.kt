package com.stlmkvd.rickandmorty.network.page

import com.google.gson.annotations.SerializedName
import com.stlmkvd.rickandmorty.data.Personage

data class PersonagePage(val info: PageInfo, @SerializedName("results") val personages: List<Personage>)