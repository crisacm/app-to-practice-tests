package com.github.crisacm.todotesting.ui.screens.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.createGraph
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.fragment
import com.github.crisacm.todotesting.R
import com.github.crisacm.todotesting.ui.navigation.Home
import com.github.crisacm.todotesting.ui.screens.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val navController = navHostFragment!!.findNavController()

        navController.graph = navController.createGraph(
            startDestination = Home
        ) {
            fragment<HomeFragment, Home> {
                label = "Home"
            }
        }
    }
}