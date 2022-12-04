package com.stlmkvd.rickandmorty

import android.app.Application

class RickAndMortyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Repository.init(applicationContext)
    }
}