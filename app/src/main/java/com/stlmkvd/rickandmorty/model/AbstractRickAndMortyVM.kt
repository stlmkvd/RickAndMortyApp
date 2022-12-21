package com.stlmkvd.rickandmorty.model

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import com.stlmkvd.rickandmorty.data.DataItem
import java.util.concurrent.Executors

private const val TAG = "AbstractVM"

abstract class AbstractRickAndMortyVM<I : DataItem> : ViewModel() {

    private var cache: MutableMap<Int, List<I>> = mutableMapOf()

    private val executor = Executors.newSingleThreadExecutor()
    private val handlerUI = Handler(Looper.getMainLooper())


    protected abstract fun loadPage(page: Int): List<I>

    protected abstract fun loadItemByUrl(url: String): I?

    fun getItemsPageAsync(pageNum: Int, callback: (List<I>) -> Unit) {

        cache[pageNum]?.let {
            handlerUI.post {
                callback(it)
            }
            return
        }

        executor.execute {
            val page = loadPage(pageNum)
            handlerUI.post {
                if (page.isNotEmpty()) cache[pageNum] = page
                callback(page)
            }
        }
    }

    fun getItemsByUrlsAsync(urls: List<String>, callback: (List<I>) -> Unit) {
        executor.execute {
            val result = mutableListOf<I>()
            val allCachedItems = cache.values.flatten()
            for (url in urls) {
                val item = allCachedItems.find { it.url == url } ?: loadItemByUrl(url) ?: break
                result.add(item)
            }
            Log.d(TAG, "items by urls loaded: $result")
            handlerUI.post {
                callback(result)
            }
        }
    }

    fun refresh() {
        cache.clear()
    }
}
