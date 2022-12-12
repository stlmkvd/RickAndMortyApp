package com.stlmkvd.rickandmorty.adapters.recycler

import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import com.stlmkvd.rickandmorty.R
import com.stlmkvd.rickandmorty.data.Personage
import com.stlmkvd.rickandmorty.databinding.ViewholderPersonageBinding
import com.stlmkvd.rickandmorty.model.AbstractVM
import com.stlmkvd.rickandmorty.model.PersonagesViewModel
import java.util.concurrent.Executors

class PersonageAdapter(private val viewModel: PersonagesViewModel) :
    AbstractAdapter<Personage>(viewModel){


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractViewHolder<Personage> {
        val binding =
            ViewholderPersonageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return PersonageViewHolder(binding)
    }

    inner class PersonageViewHolder(private val binding: ViewholderPersonageBinding) :
        AbstractViewHolder<Personage>(binding.root) {

        override fun bind(item: Personage) {
            binding.personage = item
            binding.image.setImageResource(R.drawable.black_fill)
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onItemClickListener?.onClick(item)
            }
            var image: Bitmap? = null
            val handler = Handler(Looper.getMainLooper()) {
                if (image != null) {
                    binding.image.setImageBitmap(image)
                }
                true
            }
            Executors.newSingleThreadExecutor().execute {
                image = viewModel.loadImageSync(item.imageUrl, item.imageFileName)
                handler.sendEmptyMessage(1)
            }
        }
    }
}