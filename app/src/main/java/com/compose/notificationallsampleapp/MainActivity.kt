package com.compose.notificationallsampleapp

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.compose.notificationallsampleapp.utils.NotificationHelper
import com.compose.notificationexperiment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var binding: ActivityMainBinding
    private val notificationHelper = NotificationHelper()
    private var denyCounter = 0

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        notificationPermissionRequest()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        notificationHelper.createNotificationChannel(this)

        mediaSession = MediaSessionCompat(this, "MediaSessionTag")

        // Set the MediaSession to be active
        mediaSession.isActive = true

        // Set a callback to handle media button events
        mediaSession.setCallback(object : MediaSessionCompat.Callback() {
            override fun onPlay() {
                // Handle play action
            }
            override fun onPause() {
                // Handle pause action
            }

            // Add more actions if needed
        })

        binding.basicNotification.setOnClickListener {
            notificationHelper.showBasicNotification(this)
        }

        binding.basicNotificationWithIcon.setOnClickListener {
            notificationHelper.showBasicNotificationWithBigIcon(this)
        }

        binding.bigTextNotification.setOnClickListener {
            notificationHelper.showBigTextNotification(this)
        }

        binding.inboxStyleNotification.setOnClickListener {
            notificationHelper.showInboxStyleNotification(this)
        }


        binding.messagingStyleNotification.setOnClickListener {
            notificationHelper.showMessagingStyleNotification(this)
        }


        binding.bigPictureNotification.setOnClickListener {
            notificationHelper.showBigPictureNotification(this)
        }

        binding.mediaStyleNotification.setOnClickListener {
            notificationHelper.showMediaStyleNotification(this, mediaSession)
        }


        binding.progressNotification.setOnClickListener {
            notificationHelper.showProgressNotification(this)
        }
        binding.notificationWithActionButtons.setOnClickListener {
            notificationHelper.showNotificationWithActionButtons(this)
        }
        binding.bundledNotifications.setOnClickListener {
            notificationHelper.showBundledNotifications(this)
        }


    }


    private fun notificationPermissionRequest() {
        val notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkPostNotificationPermission()
        }

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkPostNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                1
            )
        } else {
            // Permission has already been granted
        }

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission has been granted
                } else {
                    showDialog(
                        "Permission Denied",
                        "You need to allow notification permission to use this feature"
                    )
                }
                return
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showDialog(title: String, message: String) {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                1
            )
            denyCounter++
            if (denyCounter >= 2) {
                val intent = Intent()
                intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                intent.putExtra("android.provider.extra.APP_PACKAGE", packageName)
                startActivity(intent)
            }


        }
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
    }

}

