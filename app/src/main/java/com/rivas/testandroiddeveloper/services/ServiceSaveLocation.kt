package com.rivas.testandroiddeveloper.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.rivas.testandroiddeveloper.AndroidApp
import com.rivas.testandroiddeveloper.R
import com.rivas.testandroiddeveloper.utils.Constants
import com.rivas.testandroiddeveloper.utils.LocationHelper
import com.rivas.testandroiddeveloper.utils.MyLocationListener
import dagger.android.AndroidInjection
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

import android.media.RingtoneManager
import android.net.Uri
import com.rivas.testandroiddeveloper.data.LocationFirestore


class ServiceSaveLocation : Service() {

    @Inject
    lateinit var firestore: FirebaseFirestore

    var count = 2

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
        isServiceStarted = true
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setOngoing(false)
                .setSmallIcon(R.drawable.ic_launcher_background)
        val notificationManager: NotificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_ID, NotificationManager.IMPORTANCE_LOW
            )
            notificationChannel.description = NOTIFICATION_CHANNEL_ID
            notificationChannel.setSound(null, null)
            notificationManager.createNotificationChannel(notificationChannel)
            startForeground(1, builder.build())
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        isServiceStarted = false
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        LocationHelper().startListeningUserLocation(
            this, object : MyLocationListener {
                @SuppressLint("SimpleDateFormat")
                override fun onLocationChanged(location: Location?) {
                    mLocation = location
                    val date = Date(System.currentTimeMillis())
                    val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
                    mLocation?.let {
                        val locationFirestore = LocationFirestore(location?.latitude, location?.longitude, dateFormat.format(date))
                        firestore.collection(Constants.LOCATIONS).add(locationFirestore).addOnSuccessListener {
                            showLocationsSendNotification(true)
                        }.addOnFailureListener {
                            showLocationsSendNotification(false)
                        }
                    }
                }
            })
        return START_STICKY
    }

    private fun showLocationsSendNotification(b: Boolean) {
        val textTitle = if (b) {
            AndroidApp.appContext.getText(R.string.success)
        } else {
            AndroidApp.appContext.getText(R.string.failure)
        }
        val textContent = if (b) {
            AndroidApp.appContext.getText(R.string.success_update_location)
        } else {
            AndroidApp.appContext.getText(R.string.failure_update_location)
        }
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(AndroidApp.appContext, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setAutoCancel(true)

        val notificationManager: NotificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
        count++
    }

    companion object {
        var mLocation: Location? = null
        var isServiceStarted = false
        private const val NOTIFICATION_CHANNEL_ID = "test"
        private const val NOTIFICATION_CHANNEL_ID_ = "locationUpdate"
        private const val TAG = "ServiceSaveLocation"
    }
}


class BootDeviceReceivers : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            ContextCompat.startForegroundService(it, Intent(it, ServiceSaveLocation::class.java))
        }
    }
}
