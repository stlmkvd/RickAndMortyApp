package com.stlmkvd.rickandmorty.adapters.recycler

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.stlmkvd.rickandmorty.data.DataItem
import com.stlmkvd.rickandmorty.model.AbstractVM

private const val TAG = "AbstractAdapter"

abstract class AbstractAdapter<I : DataItem>(private val viewModel: AbstractVM<I>) :
    RecyclerView.Adapter<AbstractAdapter.AbstractViewHolder<I>>(), AbstractVM.CallBack {

    var onItemClickListener: OnItemClickListener<I>? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        viewModel.registerCallback(this)
    }

    abstract override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractViewHolder<I>

    override fun onBindViewHolder(holder: AbstractViewHolder<I>, position: Int) {
        holder.bind(viewModel.getItemAt(position))
    }

    override fun getItemCount(): Int {
        return viewModel.getItemCount()
    }

    override fun itemsAdded(start: Int, count: Int) {
        notifyItemRangeInserted(start, count)
        Log.d(TAG, "itemsAdded($start, $count)")
    }


    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        viewModel.removeCallback(this)
    }

    abstract class AbstractViewHolder<in I : DataItem>(view: View) :
        RecyclerView.ViewHolder(view) {

        abstract fun bind(item: I)
    }

    fun interface OnItemClickListener<in I : DataItem> {
        fun onClick(item: I)
    }
}