package com.stlmkvd.rickandmorty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.stlmkvd.rickandmorty.databinding.ActivityMainBinding
import com.stlmkvd.rickandmorty.fragments.overviews.LocationsOverviewFragment
import com.stlmkvd.rickandmorty.fragments.overviews.PersonagesOverviewFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        binding.bottomNavigationView.setOnItemSelectedListener {
            menuitem ->
            when(menuitem.itemId) {
                R.id.nav_item_personages -> supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, PersonagesOverviewFragment::class.java, null)
                    .commit()
                R.id.nav_item_locations -> supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, LocationsOverviewFragment::class.java, null)
                    .commit()
                else -> throw java.lang.UnsupportedOperationException()
            }
            true
        }
    }
}