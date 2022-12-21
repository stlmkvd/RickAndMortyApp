package com.stlmkvd.rickandmorty

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.stlmkvd.rickandmorty.databinding.ActivityMainBinding
import com.stlmkvd.rickandmorty.fragments.details.EpisodeDetailsFragment
import com.stlmkvd.rickandmorty.fragments.details.LocationDetailsFragment
import com.stlmkvd.rickandmorty.fragments.details.PersonageDetailsFragment
import com.stlmkvd.rickandmorty.fragments.overviews.EpisodesOverviewFragment
import com.stlmkvd.rickandmorty.fragments.overviews.LocationsOverviewFragment
import com.stlmkvd.rickandmorty.fragments.overviews.PersonagesOverviewFragment

const val REQUEST_KEY_OPEN_PERSONAGE = "open_personage"
const val REQUEST_KEY_OPEN_LOCATION = "open_location"
const val REQUEST_KEY_OPEN_EPISODE = "open_episode"

const val BUNDLE_ARG_PERSONAGE = "PERSONAGE"
const val BUNDLE_ARG_EPISODE = "EPISODE"
const val BUNDLE_ARG_LOCATION = "LOCATION"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        setSupportActionBar(binding.toolbar)
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
                .replace(
                    R.id.fragment_container,
                    PersonageDetailsFragment.newInstance(bundle),
                    null
                )
                .addToBackStack("open_personage").commit()
        }

        supportFragmentManager.setFragmentResultListener(
            REQUEST_KEY_OPEN_LOCATION,
            this
        ) { _, bundle ->
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LocationDetailsFragment.newInstance(bundle), null)
                .addToBackStack("open_location").commit()
        }

        supportFragmentManager.setFragmentResultListener(
            REQUEST_KEY_OPEN_EPISODE,
            this
        ) { _, bundle ->
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, EpisodeDetailsFragment.newInstance(bundle), null)
                .addToBackStack("open_episode").commit()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        currentFocus?.let {
            if (it is EditText) it.clearFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}

fun Activity.setMenuProvider(menuProvider: MenuProvider, lifecycleOwner: LifecycleOwner) {
    val menuHost = this as MenuHost
    menuHost.addMenuProvider(menuProvider, lifecycleOwner)
}

