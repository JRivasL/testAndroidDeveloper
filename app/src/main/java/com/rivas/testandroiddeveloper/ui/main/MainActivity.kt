package com.rivas.testandroiddeveloper.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.rivas.testandroiddeveloper.R.id.*
import com.rivas.testandroiddeveloper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        _binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                navigation_map -> {
                    findNavController(nav_host_fragment_activity_main).navigate(navigation_map)
                }
                navigation_movies -> {
                    findNavController(nav_host_fragment_activity_main).navigate(navigation_movies)
                }
                navigation_images -> {
                    findNavController(nav_host_fragment_activity_main).navigate(navigation_images)
                }
            }
            return@setOnItemSelectedListener true
        }
    }

}