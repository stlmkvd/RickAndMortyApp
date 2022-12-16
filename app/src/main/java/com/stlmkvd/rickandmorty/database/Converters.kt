package com.stlmkvd.rickandmorty.database

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.stlmkvd.rickandmorty.data.Location
import com.stlmkvd.rickandmorty.network.gson
import java.net.URL
import java.util.Date

class Converters {
    @TypeConverter
    fun toJsonString(locationInfo: Location.Info): String {
        return gson.toJson(locationInfo)
    }

    @TypeConverter
    fun jsonStringToLocationInfo(json: String): Location.Info {
        return gson.fromJson(json, Location.Info::class.java)
    }

    @TypeConverter
    fun toJsonString(strList: List<String>): String {
        return gson.toJson(strList)
    }

    @TypeConverter
    fun strListFromJson(json: String): List<String>{
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun toLong(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun dateFromLong(time: Long): Date {
        return Date(time)
    }

    @TypeConverter
    fun toJsonString(url: URL?): String {
        return gson.toJson(url)
    }

    @TypeConverter
    fun urlFromJsonString(string: String?): URL? {
        return gson.fromJson(string, URL::class.java)
    }

    @TypeConverter
    fun urlListToJsonString(list: List<URL>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun urlListFromJsonString(string: String): List<URL?> {
        val type = object : TypeToken<List<URL?>>() {}.type
        return gson.fromJson(string, type)
    }

}