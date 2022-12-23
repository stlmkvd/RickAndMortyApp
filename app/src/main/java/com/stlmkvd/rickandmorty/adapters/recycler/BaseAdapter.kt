package com.stlmkvd.rickandmorty.adapters.recycler

import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.stlmkvd.rickandmorty.data.DataItem

private const val TAG = "AbstractAdapter"
private const val PAGING_THRESHOLD = 4

abstract class BaseAdapter<I : DataItem>(
    private val itemsProvider: ItemsProvider<I>,
    var urls: List<String>? = null
) :
    RecyclerView.Adapter<BaseAdapter<I>.AbstractViewHolder>() {

    var filter: DataItem.FilterSelection<I>? = null
        set(value) {
            field = value
            refresh()
        }

    private var items: MutableList<I> = mutableListOf()
    private var pagesLoaded = 0

    var onItemClickListener: OnItemClickListener<I>? = null

    val isRefreshing = MutableLiveData<Boolean>()

    val nothingFound = MutableLiveData<Boolean>()

    private val handlerUI = Handler(Looper.getMainLooper())

    private var _state: LoadingState = LoadingState.READY
        set(value) {
            field = value
            handlerUI.post {
                if (value == LoadingState.END && items.isEmpty()) {
                    nothingFound.postValue(true)
                    isRefreshing.postValue(false)
                }
            }
        }


    abstract override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractViewHolder

    override fun onBindViewHolder(holder: AbstractViewHolder, position: Int) {
        if (position >= itemCount - PAGING_THRESHOLD) loadNextPage()
        val item = items[position]
        holder.bind(item)
    }

    private fun loadNextPage() {

        if (_state != LoadingState.READY) return

        _state = LoadingState.BUSY

        itemsProvider.loadPageOfItems(pagesLoaded + 1, filter) {
            Log.d(TAG, "list len = ${it?.size}")

            if (it == null) {
                _state = LoadingState.END
                return@loadPageOfItems
            }

            pagesLoaded++

            _state = LoadingState.READY

            if (it.isEmpty()) loadNextPage()
            else insertNewItems(it)
        }
    }

    private fun loadItemsByUrls() {
        if (_state == LoadingState.END) return
        _state = LoadingState.BUSY
        itemsProvider.loadItemsByUrl(urls!!) {
            insertNewItems(it)
            _state = LoadingState.END
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

    private fun insertNewItems(newItems: List<I>) {
        handlerUI.post {
            val insertStart = itemCount
            items.addAll(newItems)
            notifyItemRangeInserted(insertStart, newItems.size)
            isRefreshing.postValue(false)
            nothingFound.postValue(false)
        }
    }

    fun refresh() {
        handlerUI.post {
            isRefreshing.postValue(true)
            itemsProvider.refresh()
            items.clear()
            notifyDataSetChanged()
            pagesLoaded = 0
            _state = LoadingState.READY
            loadNextPage()
        }
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        if (urls != null) {
            loadItemsByUrls()
        } else loadNextPage()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        items.clear()
    }

    enum class LoadingState {
        READY, BUSY, END
    }

    fun interface OnItemClickListener<I> {
        fun onClick(item: I)
    }

    abstract inner class AbstractViewHolder(val view: View) :
        RecyclerView.ViewHolder(view) {

        abstract fun bind(item: I)
    }


    interface ItemsProvider<I : DataItem> {

        fun loadPageOfItems(
            pageNum: Int,
            filters: DataItem.FilterSelection<I>?,
            callback: (List<I>?) -> Unit
        )

        fun loadItemsByUrl(urls: List<String>, callback: (List<I>) -> Unit)

        fun loadImage(url: String, name: String, callback: (Bitmap?) -> Unit)

        fun refresh()
    }
}