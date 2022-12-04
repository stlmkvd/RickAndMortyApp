package com.stlmkvd.rickandmorty

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.stlmkvd.rickandmorty.network.RickAndMortyApi

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var tv: TextView

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        tv = findViewById(R.id.tv)

        handler = Handler(mainLooper) { message ->
            val personagesCount = message.data.getInt("personagesCount")
            val personagesByIdsCount = message.data.getInt("personagesByIdsCount")
            val locationsCount = message.data.getInt("locationsCount")
            val locationsByIdsCount = message.data.getInt("locationsByIdsCount")
            val episodesCount = message.data.getInt("episodesCount")
            val episodesByIdsCount = message.data.getInt("episodesByIdsCount")
            val resText = kotlin.text.buildString {
                append("Got $personagesCount personages by paging\n")
                append("Got $personagesByIdsCount personages by ids\n\n")
                append("Got $locationsCount locations by paging\n")
                append("Got $locationsByIdsCount locations by ids\n\n")
                append("Got $episodesCount episodes by paging\n")
                append("Got $episodesByIdsCount episodes by ids\n")
            }
            tv.text = resText
            true
        }

        Thread {
            val rickAndMortyService = RickAndMortyApi.service
            val personages = Repository.getInstance().getPersonagesPage()
            val personagesFromDb = Repository.getInstance().loadAllPersonagesFromRepository()
            Log.d(TAG, "Got ${personagesFromDb.size} personages from db")

            val personagesByIds = rickAndMortyService.getPersonagesByIds("1, 2, 3")
            val locations = rickAndMortyService.getLocationsPage()
            val locationsByIds = rickAndMortyService.getLocationsByIds("1, 2, 3, 4")
            val episodes =
                rickAndMortyService.getEpisodesPage()
            val episodesByIds =
                rickAndMortyService.getEpisodesByIds("1,2")

            val message = Message()

            val personagesCount = personages.size
            val personagesByIdsCount = personagesByIds.execute().body()?.size ?: 0

            val locationsCount = locations.execute().body()?.locations?.size ?: 0
            val locationsByIdsCount = locationsByIds.execute().body()?.size ?: 0

            val episodesCount = episodes.execute().body()?.episodes?.size ?: 0
            val episodesByIdsCount = episodesByIds.execute().body()?.size ?: 0

            message.data.apply {
                putInt("personagesCount", personagesCount)
                putInt("personagesByIdsCount", personagesByIdsCount)
                putInt("locationsCount", locationsCount)
                putInt("locationsByIdsCount", locationsByIdsCount)
                putInt("episodesCount", episodesCount)
                putInt("episodesByIdsCount", episodesByIdsCount)
            }
            handler.sendMessage(message)
        }.start()
    }
}