package com.stlmkvd.rickandmorty.fragments.overviews

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stlmkvd.rickandmorty.data.ItemsProvider
import com.stlmkvd.rickandmorty.data.Personage
import com.stlmkvd.rickandmorty.databinding.FragmentPersonagesBinding
import com.stlmkvd.rickandmorty.databinding.ViewholderPersonageBinding
import com.stlmkvd.rickandmorty.models.PersonageViewModel
import java.util.concurrent.Executors

private const val TAG = "PersonagesFragment"

class PersonagesFragment : Fragment() {

    private lateinit var viewModel: PersonageViewModel
    private lateinit var binding: FragmentPersonagesBinding
    private lateinit var handler: Handler
    private lateinit var adapter: PersonageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(PersonageViewModel::class.java)
        binding = FragmentPersonagesBinding.inflate(inflater, container, false)
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = PersonageAdapter()
        binding.recycler.adapter = adapter
        viewModel.registerDataSetChangesCallback(adapter)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.removeDataSetChangesCallback(adapter)
    }



    inner class PersonageAdapter : RecyclerView.Adapter<PersonageAdapter.PersonageViewHolder>(),
        ItemsProvider.CallBack {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonageViewHolder {
            val binding =
                ViewholderPersonageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return PersonageViewHolder(binding)
        }

        override fun onBindViewHolder(holder: PersonageViewHolder, position: Int) {
            holder.bind(viewModel.getItemAt(position))
        }

        override fun getItemCount(): Int {
            return viewModel.getItemCount()
        }

        override fun itemsAdded(start: Int, count: Int) {
            notifyItemRangeInserted(start, count)
        }

        inner class PersonageViewHolder(private val binding: ViewholderPersonageBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(personage: Personage) {
                binding.personage = personage
                binding.executePendingBindings()
                var image: Bitmap? = null
                val handler = Handler(Looper.getMainLooper()) {
                    binding.ivCharacter.setImageBitmap(image!!)
                    true
                }
                Executors.newSingleThreadExecutor().execute {
                    image = viewModel.loadImageSync(personage.imageUrl)
                    handler.sendEmptyMessage(1)
                }
            }
        }
    }
}