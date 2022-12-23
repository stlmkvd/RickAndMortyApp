package com.stlmkvd.rickandmorty

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.stlmkvd.rickandmorty.data.DataItem
import com.stlmkvd.rickandmorty.data.Episode
import com.stlmkvd.rickandmorty.data.Location
import com.stlmkvd.rickandmorty.data.Personage
import com.stlmkvd.rickandmorty.databinding.ActivityMainBinding
import com.stlmkvd.rickandmorty.fragments.details.EpisodeDetailsFragment
import com.stlmkvd.rickandmorty.fragments.details.LocationDetailsFragment
import com.stlmkvd.rickandmorty.fragments.details.PersonageDetailsFragment
import com.stlmkvd.rickandmorty.fragments.overviews.EpisodesOverviewFragment
import com.stlmkvd.rickandmorty.fragments.overviews.LocationsOverviewFragment
import com.stlmkvd.rickandmorty.fragments.overviews.PersonagesOverviewFragment

const val REQUEST_KEY_OPEN_EPISODE = "open_episode"
const val REQUEST_KEY_OPEN_DETAILS = "open_details"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportActionBar?.apply {
                    setDisplayHomeAsUpEnabled(true)
                    setDisplayShowHomeEnabled(true)
                }
            } else supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(false)
                setDisplayShowHomeEnabled(false)
            }
        }

//        onBackPressedDispatcher.addCallback {
//            supportFragmentManager.popBackStack()
//            var validBottomNavItem: Int? =
//                when (supportFragmentManager.findFragmentById(binding.fragmentContainer.id)) {
//                    is PersonagesOverviewFragment -> R.id.nav_item_personages
//                    is LocationsOverviewFragment -> R.id.nav_item_locations
//                    is EpisodesOverviewFragment -> R.id.nav_item_episodes
//                    else -> null
//                }
//            validBottomNavItem?.let {
//                binding.bottomNavigationView.selectedItemId = validBottomNavItem
//            }
//        }

    }


    override fun onStart() {
        super.onStart()

        binding.bottomNavigationView.setOnItemSelectedListener { menuitem ->
            val replacement: Fragment = when (menuitem.itemId) {
                R.id.nav_item_personages -> PersonagesOverviewFragment()
                R.id.nav_item_locations -> LocationsOverviewFragment()
                R.id.nav_item_episodes -> EpisodesOverviewFragment()
                else -> throw java.lang.UnsupportedOperationException()
            }

            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            supportFragmentManager
                .beginTransaction()
                .replace(binding.fragmentContainer.id, replacement, null)
                .setReorderingAllowed(true)
                .commit()
            true
        }

        supportFragmentManager.setFragmentResultListener(
            REQUEST_KEY_OPEN_DETAILS,
            this
        ) { _, bundle ->
            val replacement = when (bundle[DataItem.BUNDLE_ARG]) {
                is Personage -> PersonageDetailsFragment.createInstance(bundle)
                is Location -> LocationDetailsFragment.createInstance(bundle)
                is Episode -> EpisodeDetailsFragment.createInstance(bundle)
                else -> throw java.lang.UnsupportedOperationException()
            }
            supportFragmentManager
                .beginTransaction()
                .replace(binding.fragmentContainer.id, replacement)
                .addToBackStack("open_details")
                .setReorderingAllowed(true)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
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

