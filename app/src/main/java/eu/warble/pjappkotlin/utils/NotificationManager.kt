package eu.warble.pjappkotlin.utils

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.app.NotificationChannel
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import eu.warble.pjappkotlin.R
import android.content.Intent
import android.content.BroadcastReceiver


object NotificationManager {
    private var notificationsIdCounter: Int = 0
    private val channelId = "pjapp"

    fun createDownloadFileNotification(
            appContext: Context,
            title: String,
            contentText: String
    ): NotificationCompat.Builder {
        val notificationManager = appContext.getSystemService(
                Context.NOTIFICATION_SERVICE
        ) as NotificationManager
        val bundle = Bundle().apply {
            putInt("id", notificationsIdCounter++)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(notificationManager)
        }
        val notification = NotificationCompat.Builder(appContext, channelId)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.network_download)
                .setContentText(contentText)
                .setOngoing(true)
                .setContentIntent(null)
                .addExtras(bundle)
                .setProgress(100, 30, true)
        notificationManager.notify(notificationsIdCounter, notification.build())
        return notification
    }

    fun updateDownloadNotification(context: Context, builder: NotificationCompat.Builder) {
        val notificationManager = context.getSystemService(
                Context.NOTIFICATION_SERVICE
        ) as NotificationManager
        notificationManager.notify(builder.extras.getInt("id"), builder.build())
    }

    fun downloadNotificationSuccess(
            context: Context,
            builder: NotificationCompat.Builder,
            fileLocation: String,
            fileName: String
    ) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        builder.setSmallIcon(R.drawable.file)
                .setProgress(0, 0, false)
                .setContentText(fileName)
                .setOngoing(false)
        builder.mActions.clear()
        builder.setContentTitle(context.getString(R.string.download_complete))
                .setContentIntent(FileManager.openFilePendingIntent(fileLocation, context))

        notificationManager.notify(builder.extras.getInt("id"), builder.build())
    }

    fun downloadNotificationFailure(
            context: Context,
            builder: NotificationCompat.Builder,
            fileName: String
    ) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        builder.setSmallIcon(R.drawable.file)
                .setProgress(0, 0, false)
                .setContentText(fileName)
                .setOngoing(false)
        builder.mActions.clear()
        builder.setContentTitle(context.getString(R.string.download_error))

        notificationManager.notify(builder.extras.getInt("id"), builder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(manager: NotificationManager?) {
        if (manager != null && manager.getNotificationChannel(channelId) == null) {
            val channel = NotificationChannel(
                    channelId, "PJATK", NotificationManager.IMPORTANCE_LOW)
            manager.createNotificationChannel(channel)
        }
    }
}