package com.stlmkvd.rickandmorty.database.typeconverters

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.stlmkvd.rickandmorty.data.Location
import com.stlmkvd.rickandmorty.network.gson
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
}