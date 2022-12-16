package com.stlmkvd.rickandmorty.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.stlmkvd.rickandmorty.data.Episode
import com.stlmkvd.rickandmorty.data.Location
import com.stlmkvd.rickandmorty.data.Personage
import com.stlmkvd.rickandmorty.database.dao.EpisodesDao
import com.stlmkvd.rickandmorty.database.dao.LocationsDao
import com.stlmkvd.rickandmorty.database.dao.PersonagesDao

@Database(
    entities = [Personage::class, Location::class, Episode::class],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personagesDao(): PersonagesDao

    abstract fun locationsDao(): LocationsDao

    abstract fun episodesDao(): EpisodesDao
}