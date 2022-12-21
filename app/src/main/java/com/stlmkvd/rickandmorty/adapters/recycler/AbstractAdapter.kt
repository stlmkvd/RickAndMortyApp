package com.stlmkvd.rickandmorty.adapters.recycler

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stlmkvd.rickandmorty.data.DataItem
import com.stlmkvd.rickandmorty.model.AbstractRickAndMortyVM
import com.stlmkvd.rickandmorty.viewholders.AbstractViewHolder

private const val TAG = "AbstractAdapter"
private const val PAGING_THRESHOLD = 8

abstract class AbstractAdapter<I : DataItem>(
    private val viewModel: AbstractRickAndMortyVM<I>,
    private val urls: List<String>? = null
) :
    RecyclerView.Adapter<AbstractViewHolder<I>>() {

    var onItemClickListener: OnItemClickListener<I>? = null
    var filter: DataItem.FilterSelection<I>? = null
        set(value) {
            field = value
            refresh()
        }
    private var items: MutableList<I> = mutableListOf()
    private var pagesLoaded = 0
    private var state: LoadingState = LoadingState.READY


    abstract override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractViewHolder<I>

    override fun onBindViewHolder(holder: AbstractViewHolder<I>, position: Int) {
        if (position >= itemCount - PAGING_THRESHOLD) loadNextPage()
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClickListener?.onClick(item)
        }
    }

    private fun loadNextPage() {

        if (state != LoadingState.READY) return

        Log.d(TAG, "started loading page ${pagesLoaded + 1}")

        state = LoadingState.BUSY

        viewModel.getItemsPageAsync(pagesLoaded + 1) {

                rawItems ->

            if (rawItems.isEmpty()) {
                state = LoadingState.END
                return@getItemsPageAsync
            }

            pagesLoaded++

            val validItems = filter?.let {
                rawItems.filter { item -> filter!!.matches(item) }
            } ?: rawItems

            Log.d(TAG, "ended loading page $pagesLoaded")

            if (validItems.isEmpty()) {
                state = LoadingState.READY
                loadNextPage()
                return@getItemsPageAsync
            }

            insertNewItems(validItems)
            state = LoadingState.READY
        }
    }



    override fun getItemCount(): Int {
        return items.size
    }

    private fun insertNewItems(newItems: List<I>) {
        val insertStart = itemCount
        items.addAll(newItems)
        notifyItemRangeInserted(insertStart, newItems.size)
    }

    fun refresh() {
        items.clear()
        notifyDataSetChanged()
        pagesLoaded = 0
        state = LoadingState.READY
        loadNextPage()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        if (urls != null) {
            viewModel.getItemsByUrlsAsync(urls) {
                insertNewItems(it)
            }
            state = LoadingState.END
        }
        else loadNextPage()
    }

    private enum class LoadingState {
        READY, BUSY, END
    }

    fun interface OnItemClickListener<I> {
        fun onClick(item: I)
    }
}