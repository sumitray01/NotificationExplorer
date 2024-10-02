package com.compose.notificationallsampleapp.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.compose.notificationallsampleapp.MainActivity
import com.compose.notificationexperiment.R

class NotificationHelper() {
    companion object {
        private const val CHANNEL_ID = "example_channel"
        private const val CHANNEL_NAME = "Example Notifications"
        private const val CHANNEL_DESCRIPTION = "Channel for example notifications"
        private const val NOTIFICATION_ID = 1001
    }


    // Function to create a notification channel
    @RequiresApi(Build.VERSION_CODES.Q)
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = CHANNEL_DESCRIPTION
                enableLights(true)
                enableVibration(true)
                setShowBadge(true)
                setAllowBubbles(true)
                setBypassDnd(true)
                lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC

            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }





    // Basic Notification
    @SuppressLint("MissingPermission")
    fun showBasicNotification(context: Context) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Basic Notification")
            .setContentText("This is a simple notification.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(context).notify(1, notification)
    }

    // Basic Notification with Big Icon
    @SuppressLint("MissingPermission")
    fun showBasicNotificationWithBigIcon(context: Context) {
        val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.geofenc_icon_png)
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Basic Notification")
            .setContentText("This is a simple notification.")
            .setLargeIcon(largeIcon)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(context).notify(2, notification)
    }


    // Big Text Notification
    @SuppressLint("MissingPermission")
    fun showBigTextNotification(context: Context) {
        val bigTextStyle = NotificationCompat.BigTextStyle()
            .bigText("This is an example of a Big Text Notification with a lot more content than a basic notification.")

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Big Text Notification")
            .setStyle(bigTextStyle)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(context).notify(2, notification)
    }

    // Inbox Style Notification
    @SuppressLint("MissingPermission")
    fun showInboxStyleNotification(context: Context) {
        val inboxStyle = NotificationCompat.InboxStyle()
            .addLine("Message 1")
            .addLine("Message 2")
            .addLine("Message 3")
            .setSummaryText("+3 more")

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Inbox Style Notification")
            .setStyle(inboxStyle)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(context).notify(3, notification)
    }

    // Big Picture Notification
    @SuppressLint("MissingPermission")
    fun showBigPictureNotification(context: Context) {
        val bigPictureStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(BitmapFactory.decodeResource(context.resources, R.drawable.image))
            .bigLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.image))
            .setBigContentTitle("Big Picture Notification")
            .setSummaryText("This is a big picture notification.")

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Big Picture Notification")
            .setStyle(bigPictureStyle)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(context).notify(4, notification)
    }

    // Messaging Style Notification
    @SuppressLint("MissingPermission")
    fun showMessagingStyleNotification(context: Context) {
        val messagingStyle = NotificationCompat.MessagingStyle("User")
            .addMessage("Hello!", System.currentTimeMillis(), "Friend")
            .addMessage("Hi, how are you?", System.currentTimeMillis(), "User")

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Messaging Style Notification")
            .setStyle(messagingStyle)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(context).notify(5, notification)
    }

    // Media Style Notification
    @SuppressLint("MissingPermission")
    fun showMediaStyleNotification(context: Context, mediaSession: MediaSessionCompat) {
        val mediaStyle = androidx.media.app.NotificationCompat.MediaStyle()
            .setMediaSession(mediaSession.sessionToken)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Media Style Notification")
            .setContentText("Song Title")
            .setStyle(mediaStyle)
            .addAction(R.drawable.pause_icon, "Pause", getPendingIntent(context))
            .addAction(R.drawable.next_icon, "Next", getPendingIntent(context))
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }

    // Progress Notification
   @SuppressLint("MissingPermission")
    fun showProgressNotification(context: Context) {
        val PROGRESS_MAX = 100
        val PROGRESS_CURRENT = 50

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Progress Notification")
            .setContentText("Download in progress")
            .setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        NotificationManagerCompat.from(context).notify(6, notification)
    }

    // Notification with Action Buttons
    @SuppressLint("MissingPermission")
    fun showNotificationWithActionButtons(context: Context) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Notification with Actions")
            .setContentText("Click an action below")
            .addAction(R.drawable.open_icon, "Open", getPendingIntent(context))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(context).notify(7, notification)
    }

    /*// Full-Screen Notification
    fun showFullScreenNotification(context: Context) {
        val fullScreenIntent = Intent(context, YourFullScreenActivity::class.java)
        val fullScreenPendingIntent = PendingIntent.getActivity(context, 0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Full-Screen Notification")
            .setContentText("This notification can appear over other apps.")
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }*/

    // Bundled Notifications
    @SuppressLint("MissingPermission")
    fun showBundledNotifications(context: Context) {
        val childNotification1 = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Bundled Notification 1")
            .setContentText("First notification in a bundle")
            .setGroup("group_key")
            .build()

        val childNotification2 = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Bundled Notification 2")
            .setContentText("Second notification in a bundle")
            .setGroup("group_key")
            .build()

        val summaryNotification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Summary Notification")
            .setContentText("You have 2 new messages")
            .setStyle(NotificationCompat.InboxStyle()
                .addLine("Bundled Notification 1")
                .addLine("Bundled Notification 2")
                .setSummaryText("2 messages"))
            .setGroup("group_key")
            .setGroupSummary(true)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(8, childNotification1)
        notificationManager.notify(9, childNotification2)
        notificationManager.notify(10, summaryNotification)
    }

    // Helper function to get a PendingIntent
    private fun getPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE)
    }




}
