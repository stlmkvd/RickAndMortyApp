package com.stlmkvd.rickandmorty

import com.stlmkvd.rickandmorty.data.Personage
import com.stlmkvd.rickandmorty.model.PersonagesViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RickAndMortyApiTest {


    val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }



    @Test
    fun adapterShouldLoadPageIntoList() {

        val personage = mockk<Personage>()

        val personages = mutableListOf<Personage>().apply { repeat(20) { add(personage) } }

        val repository = mockk<Repository>()

        mockkObject(Repository.Companion)

        every { Repository.Companion.getInstance() } returns mockk<Repository>()

        coEvery { repository.getPersonagesPagedSync(1) } returns personages

        val viewModel = PersonagesViewModel()

        viewModel.loadPageOfItems(1, null) {
            assertEquals(20, it?.size)
        }


    }


}