package com.stlmkvd.rickandmorty

import android.app.Application
import android.util.Log

class RickAndMortyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Repository.init(applicationContext)
        Log.d("khgsd", "application. onCreate()")
    }
}