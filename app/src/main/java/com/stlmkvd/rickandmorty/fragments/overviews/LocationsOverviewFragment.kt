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
import com.stlmkvd.rickandmorty.data.Location
import com.stlmkvd.rickandmorty.data.Personage
import com.stlmkvd.rickandmorty.databinding.FragmentPersonagesBinding
import com.stlmkvd.rickandmorty.databinding.ViewholderLocationBinding
import com.stlmkvd.rickandmorty.databinding.ViewholderPersonageBinding
import com.stlmkvd.rickandmorty.models.LocationsViewModel
import java.util.concurrent.Executors

class LocationsOverviewFragment : Fragment() {

    private lateinit var viewModel: LocationsViewModel
    private lateinit var binding: FragmentPersonagesBinding
    private lateinit var adapter: LocationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(LocationsViewModel::class.java)
        binding = FragmentPersonagesBinding.inflate(inflater, container, false)
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = LocationsAdapter()
        binding.recycler.adapter = adapter
        viewModel.registerDataSetChangesCallback(adapter)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.removeDataSetChangesCallback(adapter)
    }



    inner class LocationsAdapter : RecyclerView.Adapter<LocationsAdapter.LocationViewHolder>(),
        ItemsProvider.CallBack {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
            val binding =
                ViewholderLocationBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return LocationViewHolder(binding)
        }

        override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
            holder.bind(viewModel.getItemAt(position))
        }

        override fun getItemCount(): Int {
            return viewModel.getItemCount()
        }

        override fun itemsAdded(start: Int, count: Int) {
            notifyItemRangeInserted(start, count)
        }

        inner class LocationViewHolder(private val binding: ViewholderLocationBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(location: Location) {
                binding.location = location
                binding.executePendingBindings()
            }
        }
    }
}