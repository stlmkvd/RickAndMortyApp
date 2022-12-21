package com.stlmkvd.rickandmorty.viewholders

import android.os.Handler
import android.os.Looper
import com.stlmkvd.rickandmorty.BitmapProvider
import com.stlmkvd.rickandmorty.R
import com.stlmkvd.rickandmorty.data.Personage
import com.stlmkvd.rickandmorty.databinding.ViewholderPersonageBinding
import java.util.concurrent.Executors


class PersonageViewHolder(private val binding: ViewholderPersonageBinding) :
    AbstractViewHolder<Personage>(binding.root) {

    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    override fun bind(item: Personage) {
        handler.removeCallbacksAndMessages(null)
        binding.image.setImageResource(R.drawable.black_fill)
        executor.execute {
            val bitmap = BitmapProvider.loadImageSync(item.imageUrl, item.imageFileName)
            bitmap?.let {
                handler.post { binding.image.setImageBitmap(it) }
            }
        }
        binding.personage = item
        binding.executePendingBindings()
    }
}