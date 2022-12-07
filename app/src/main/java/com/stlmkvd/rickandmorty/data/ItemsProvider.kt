package com.stlmkvd.rickandmorty.data

import android.os.Handler
import android.os.Looper
import android.util.Log
import java.util.concurrent.Executors
import kotlin.properties.Delegates

private const val TAG = "DataItemsProvider"
private const val PAGE_SIZE = 20
private const val PAGING_THRESHOLD = 10


class ItemsProvider<I : DataItem>(private val loader: (page: Int) -> List<I>) {

    private val items: MutableList<I> = mutableListOf()
    private var _state: LoadingState = LoadingState.READY
    private val callbacks: MutableList<CallBack> = mutableListOf()

    init {
        loadNextPageAsync()
    }

    private fun loadNextPageAsync() {
        if (_state == LoadingState.BUSY || _state == LoadingState.END) return
        Log.d(TAG, "loadNextPageAsync()")
        _state = LoadingState.BUSY
        val startPosition = items.size
        val nextPage = items.size / PAGE_SIZE + 1
        var loadedCount: Int by Delegates.notNull()
        val handler = Handler(Looper.getMainLooper()) {
            callbacks.forEach { it.itemsAdded(startPosition, loadedCount) }
            Log.d(TAG, "handler fired! loadedCount: $loadedCount")
            true
        }
        Executors.newSingleThreadExecutor()
            .execute {
                val loadedItems = loader.invoke(nextPage)
                loadedCount = loadedItems.size
                items.addAll(loadedItems)
                handler.sendEmptyMessage(1)
                _state =
                    if (loadedCount < PAGING_THRESHOLD) LoadingState.END
                    else LoadingState.READY
            }
    }

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


    private enum class LoadingState {
        READY, BUSY, END
    }

    interface CallBack {
        fun itemsAdded(start: Int, count: Int)
    }
}




