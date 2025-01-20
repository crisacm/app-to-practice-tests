package com.github.crisacm.todotesting.ui.screens.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.crisacm.todotesting.R
import com.github.crisacm.todotesting.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        hiltRule.inject()
    }

    @Test
    fun testDisplayTasks() {
        homeViewModel.addTask("Task 1", null)
        homeViewModel.addTask("Task 2", null)

        launchFragmentInHiltContainer<HomeFragment> {
            val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view_todo)
            assertNotNull(recyclerView)
            assertEquals(2, recyclerView?.adapter?.itemCount)
        }
    }
}