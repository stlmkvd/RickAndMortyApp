package com.stlmkvd.rickandmorty.model

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stlmkvd.rickandmorty.Repository
import com.stlmkvd.rickandmorty.adapters.recycler.BaseAdapter
import com.stlmkvd.rickandmorty.data.DataItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "AbstractVM"

abstract class BaseViewModel<I : DataItem> : ViewModel(),
    BaseAdapter.ItemsProvider<I> {

    private val pagedCache: MutableMap<Int, List<I>> = mutableMapOf()

    private val rawCache: MutableSet<I> = mutableSetOf()

    private val imagesCache: MutableMap<String, Bitmap> = mutableMapOf()

    abstract suspend fun loadItemsPagedSync(pageNum: Int): List<I>?

    abstract suspend fun loadItemByUrl(url: String): I?


    override fun loadPageOfItems(
        pageNum: Int,
        filters: DataItem.FilterSelection<I>?,
        callback: (List<I>?) -> Unit
    ) {
        viewModelScope.launch {
            val page = pagedCache[pageNum] ?: loadItemsPagedSync(pageNum)

            if (page == null || page.isEmpty()) {
                callback(null)
                return@launch
            }


            rawCache.addAll(page)
            pagedCache[pageNum] = page

            val res = withContext(Dispatchers.Default) {
                if (filters != null) {
                    page.filter { filters.matches(it) }
                } else page
            }
            callback(res)
        }
    }

    override fun loadItemsByUrl(urls: List<String>, callback: (List<I>) -> Unit) {
        viewModelScope.launch {
            val result = mutableListOf<I>()
            for (url in urls) {
                val item =
                    rawCache.find { it.url == url } ?: loadItemByUrl(url)!!
                result.add(item)
                rawCache.add(item)
            }
            callback(result)
        }
    }

    override fun refresh() {
        pagedCache.clear()
        imagesCache.clear()
        rawCache.clear()
    }

    override fun loadImage(url: String, name: String, callback: (Bitmap?) -> Unit) {
        imagesCache[url]?.let {
            callback(it)
            return
        }
        viewModelScope.launch {
            val image = Repository.getInstance().loadImageSync(url, name)
            if (image != null) imagesCache[url] = image
            callback(image)
        }
    }
}
