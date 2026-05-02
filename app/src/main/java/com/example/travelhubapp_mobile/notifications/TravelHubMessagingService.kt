package com.example.travelhubapp_mobile.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.travelhubapp_mobile.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class TravelHubMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        val title = message.notification?.title
            ?: message.data["title"]
            ?: "TravelHub"
        val body = message.notification?.body
            ?: message.data["body"]
            ?: ""
        val bookingId = message.data["booking_id"]
        showNotification(title, body, bookingId)
    }

    override fun onNewToken(token: String) {
        TokenRepository.saveToken(applicationContext, token)
    }

    private fun showNotification(title: String, body: String, bookingId: String?) {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(manager)
        }

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            bookingId?.let { putExtra("booking_id", it) }
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        manager.notify(System.currentTimeMillis().toInt(), notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(manager: NotificationManager) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Reservas TravelHub",
            NotificationManager.IMPORTANCE_HIGH
        ).apply { description = "Notificaciones de reservas" }
        manager.createNotificationChannel(channel)
    }

    companion object {
        const val CHANNEL_ID = "travelhub_reservas"
    }
}
