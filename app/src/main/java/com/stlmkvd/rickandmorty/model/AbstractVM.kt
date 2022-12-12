package com.stlmkvd.rickandmorty.model

import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import com.stlmkvd.rickandmorty.Repository
import com.stlmkvd.rickandmorty.data.DataItem
import java.util.concurrent.Executors
import kotlin.properties.Delegates

private const val TAG = "AbstractVM"
private const val PAGE_SIZE = 20
private const val PAGING_THRESHOLD = 10

abstract class AbstractVM<I : DataItem> : ViewModel() {

    private val items: MutableList<I> = mutableListOf()
    private var _state: LoadingState = LoadingState.READY
    private val callbacks: MutableList<CallBack> = mutableListOf()

    init {
        loadNextPageAsync()
    }

    private fun loadNextPageAsync() {
        if (_state == LoadingState.BUSY || _state == LoadingState.END) return
        _state = LoadingState.BUSY
        val startPosition = items.size
        val nextPage = items.size / PAGE_SIZE + 1
        var loadedItems: List<I> by Delegates.notNull()
        val handler = Handler(Looper.getMainLooper()) {
            items.addAll(loadedItems)
            callbacks.forEach { it.itemsAdded(startPosition, loadedItems.size) }
            _state =
                if (loadedItems.size < PAGING_THRESHOLD) LoadingState.END
                else LoadingState.READY
            true
        }
        Executors.newSingleThreadExecutor()
            .execute {
                loadedItems = loadPage(nextPage)
                handler.sendEmptyMessage(1)
            }
    }

    abstract fun loadPage(page: Int): List<I>

    abstract fun loadItemsByIds(ids: List<Int>): List<I>

    fun getItemAt(position: Int): I {
        if (position >= getItemCount() - PAGING_THRESHOLD) {
            loadNextPageAsync()
        }
        return items[position]
    }

    fun getItemCount(): Int {
        return items.size
    }


    fun registerCallback(callBack: CallBack) {
        callbacks.add(callBack)
    }

    fun removeCallback(callBack: CallBack) {
        callbacks.remove(callBack)
    }

    fun loadImageSync(url: String, name: String): Bitmap? {
        return Repository.getInstance().loadImageSync(url, name)
    }

    private enum class LoadingState {
        READY, BUSY, END
    }

    interface CallBack {
        fun itemsAdded(start: Int, count: Int)
    }
}