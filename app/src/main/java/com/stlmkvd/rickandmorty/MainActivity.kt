package com.stlmkvd.rickandmorty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.stlmkvd.rickandmorty.databinding.ActivityMainBinding
import com.stlmkvd.rickandmorty.fragments.details.PersonagesDetailsFragment
import com.stlmkvd.rickandmorty.fragments.overviews.EpisodesOverviewFragment
import com.stlmkvd.rickandmorty.fragments.overviews.LocationsOverviewFragment
import com.stlmkvd.rickandmorty.fragments.overviews.PersonagesOverviewFragment

const val REQUEST_KEY_OPEN_PERSONAGE = "open_personage"
const val REQUEST_KEY_OPEN_LOCATION = "open_location"
const val REQUEST_KEY_OPEN_EPISODE = "open_episode"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        binding.bottomNavigationView.setOnItemSelectedListener { menuitem ->
            val replacement: Class<out Fragment> = when (menuitem.itemId) {
                R.id.nav_item_personages -> PersonagesOverviewFragment::class.java
                R.id.nav_item_locations -> LocationsOverviewFragment::class.java
                R.id.nav_item_episodes -> EpisodesOverviewFragment::class.java
                else -> throw java.lang.UnsupportedOperationException()
            }
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, replacement, null)
                .commit()
            true
        }

        supportFragmentManager.setFragmentResultListener(
            REQUEST_KEY_OPEN_PERSONAGE,
            this
        ) { _, bundle ->
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PersonagesDetailsFragment::class.java, bundle)
                .addToBackStack("open_personage").commit()
        }

        supportFragmentManager.setFragmentResultListener(
            REQUEST_KEY_OPEN_LOCATION,
            this
        ) { _, bundle ->
            TODO()
        }

        supportFragmentManager.setFragmentResultListener(
            REQUEST_KEY_OPEN_EPISODE,
            this
        ) { _, bundle ->
            TODO()
        }
    }
}