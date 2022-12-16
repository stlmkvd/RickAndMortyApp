package com.stlmkvd.rickandmorty.model

import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.reflect.TypeToken
import com.stlmkvd.rickandmorty.Repository
import com.stlmkvd.rickandmorty.data.DataItem
import java.util.concurrent.Executors

private const val TAG = "AbstractVM"
private const val PAGING_THRESHOLD = 10

abstract class AbstractRickAndMortyVM<I : DataItem>() : ViewModel() {

    private var itemsRaw: MutableList<I> = mutableListOf()
    private var itemsFiltered: MutableList<I> = mutableListOf()
    private var bitmapPool: MutableMap<String, Bitmap> = mutableMapOf()
    private var pagesLoaded = 0
    private var filter: DataItem.FilterSelection<I>? = null

    private var _state: LoadingState = LoadingState.READY
    private val dataChangedCallBacks: MutableList<OnDataChangedCallBack> = mutableListOf()
    private val refreshCompleteCallbacks: MutableList<OnRefreshCompleteCallback> = mutableListOf()
    private var isRefreshing = false

    val typeToken = object : TypeToken<I>() {}


    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper()) { message ->
        val newItems = message.obj as List<I>
        val insertIndex = itemsFiltered.size
        itemsFiltered.addAll(newItems)
        notifyItemRangeInserted(insertIndex, newItems.size)

        refreshCompleteCallbacks.forEach(OnRefreshCompleteCallback::onRefreshComplete)

        true
    }


    init {
        loadNextPageAsync()
    }

    abstract fun loadPage(page: Int): List<I>

    abstract fun loadItemsByIds(ids: List<Int>): List<I>

    private fun loadNextPageAsync() {
        Log.d(TAG, "_state = $_state")
        if (_state == LoadingState.BUSY || _state == LoadingState.END) return

        executor.execute {

            Log.d(TAG, "execute page loading")

            _state = LoadingState.BUSY

            val newItemsRaw = loadPage(pagesLoaded + 1)
            if (newItemsRaw.isEmpty()) {
                _state = LoadingState.END
                return@execute
            }
            pagesLoaded++
            itemsRaw.addAll(newItemsRaw)

            val newItemsFiltered = filter?.let {
                newItemsRaw.filter { filter!!.matches(it) }
            } ?: newItemsRaw

            if (newItemsFiltered.isEmpty()) {
                _state = LoadingState.CONTINUE
                loadNextPageAsync()
                return@execute
            }

            val message = Message().apply {
                obj = newItemsFiltered
            }
            handler.sendMessage(message)
            _state = LoadingState.READY
        }
    }


    fun getItemAt(position: Int): I {
        if (position >= getItemCount() - PAGING_THRESHOLD) {
            loadNextPageAsync()
        }
        return itemsFiltered[position]
    }

    fun getItemCount(): Int {
        return itemsFiltered.size
    }

    fun refresh() {
        dataChangedCallBacks.forEach { it.onItemsRemoved(0, getItemCount()) }
        itemsRaw.clear()
        itemsFiltered.clear()
        bitmapPool.clear()
        pagesLoaded = 0
        submitFilters(null)
        _state = LoadingState.READY
        loadNextPageAsync()
    }

    fun submitFilters(filter: DataItem.FilterSelection<I>?) {
        if (filter == this.filter) {
            return
        }
        this.filter = filter
        if (filter != null) {
            itemsFiltered = itemsRaw.filter { filter.matches(it) }.toMutableList()
            itemsFiltered.ifEmpty { loadNextPageAsync() }
        } else itemsFiltered = ArrayList<I>(itemsRaw)
        notifyDataSetChanged()
    }

    fun loadImageSync(url: String, name: String): Bitmap? {
        var bitmap = bitmapPool[name]
        if (bitmap == null) {
            bitmap = Repository.getInstance().loadImageSync(url, name)?.also {
                bitmapPool[name] = it
            }
        }
        return bitmap
    }

    private fun notifyDataSetChanged() {
        dataChangedCallBacks.forEach { it.onDataSetChanged() }
    }

    private fun notifyItemRangeInserted(start: Int, count: Int) {
        dataChangedCallBacks.forEach { it.onItemsInserted(start, count) }
    }

    private fun notifyItemRangeRemoved(start: Int, count: Int) {
        dataChangedCallBacks.forEach { it.onItemsRemoved(start, count) }
    }


    fun registerDataChangesCallback(callBack: OnDataChangedCallBack) {
        dataChangedCallBacks.add(callBack)
    }

    fun removeDataChangesCallback(callBack: OnDataChangedCallBack) {
        dataChangedCallBacks.remove(callBack)
    }

    fun registerOnRefreshCompleteCallback(callback: OnRefreshCompleteCallback) {
        refreshCompleteCallbacks.add(callback)
    }

    fun removeOnRefreshCompleteCallback(callback: OnRefreshCompleteCallback) {
        refreshCompleteCallbacks.remove(callback)
    }


    private enum class LoadingState {
        READY, BUSY, CONTINUE, END
    }

    fun interface OnRefreshCompleteCallback {
        fun onRefreshComplete()
    }

    interface OnDataChangedCallBack {

        fun onItemsInserted(start: Int, count: Int)

        fun onItemsRemoved(start: Int, count: Int)

        fun onDataSetChanged()

    }
}
