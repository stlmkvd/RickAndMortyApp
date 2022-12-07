package com.stlmkvd.rickandmorty.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.stlmkvd.rickandmorty.data.Personage
import com.stlmkvd.rickandmorty.database.dao.PersonagesDao
import com.stlmkvd.rickandmorty.database.typeconverters.Converters

@Database(
    entities = [Personage::class],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun rickAndMortyDao(): PersonagesDao
}