package com.stlmkvd.rickandmorty.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.stlmkvd.rickandmorty.adapters.recycler.AbstractAdapter
import com.stlmkvd.rickandmorty.data.DataItem

abstract class AbstractViewHolder<I : DataItem>(view: View) :
    RecyclerView.ViewHolder(view) {

    abstract fun bind(item: I)
}