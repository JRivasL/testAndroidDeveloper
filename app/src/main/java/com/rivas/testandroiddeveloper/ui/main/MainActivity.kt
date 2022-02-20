package com.rivas.testandroiddeveloper.ui.main

import android.Manifest
import android.app.IntentService
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.rivas.testandroiddeveloper.AndroidApp
import com.rivas.testandroiddeveloper.R.id.*
import com.rivas.testandroiddeveloper.databinding.ActivityMainBinding
import android.content.Intent
import com.rivas.testandroiddeveloper.services.ServiceSaveLocation
import com.rivas.testandroiddeveloper.utils.PermissionUtils.isPermissionGranted
import com.rivas.testandroiddeveloper.utils.PermissionUtils.requestPermission
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        validatePermissions()
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }
        if (isPermissionGranted(
                permissions,
                grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            ContextCompat.startForegroundService(this, Intent(this, ServiceSaveLocation::class.java))
        }
    }


    private fun validatePermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            ContextCompat.startForegroundService(this, Intent(this, ServiceSaveLocation::class.java))
        } else {
            requestPermission(
                this, LOCATION_PERMISSION_REQUEST_CODE,
                Manifest.permission.ACCESS_FINE_LOCATION, true
            )
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}