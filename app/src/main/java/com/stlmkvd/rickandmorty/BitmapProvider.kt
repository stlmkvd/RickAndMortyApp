package com.stlmkvd.rickandmorty

import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors


object BitmapProvider {

    private var cache: MutableMap<String, Bitmap?> = mutableMapOf()
    private val executor = Executors.newSingleThreadExecutor()
    private val handlerUI = Handler(Looper.getMainLooper())

    fun loadImageSync(url: String, name: String): Bitmap? {
        cache[name]?.let {
            return it
        }

        val bitmap = Repository.getInstance().loadImageSync(url, name)
        cache[name] = bitmap
        return bitmap
    }

    fun loadImageAsync(url: String, name: String, callback: (Bitmap?) -> Unit) {
        executor.execute {
            val bitmap = loadImageSync(url, name)
            handlerUI.post {
                callback(bitmap)
            }
        }
    }

    fun clearCache() {
        cache.clear()
    }
}
