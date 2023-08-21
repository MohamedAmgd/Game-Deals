package com.mohamed_amgd.gamedeals.presentation

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.mohamed_amgd.gamedeals.R
import com.mohamed_amgd.gamedeals.presentation.activities.main_activity.MainActivity

class FreeGamesNotificationService(private val context: Context) {
    companion object {
        const val FREE_GAMES_CHANNEL_ID = "free_games_channel"
        const val FREE_GAMES_CHANNEL_NAME = "Free games"
        const val FREE_GAMES_CHANNEL_DESCRIPTION = "Gets you updates if any game have 100% Discount"
    }

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    fun showNotification() {
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )
        val notification = NotificationCompat
            .Builder(context, FREE_GAMES_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setContentTitle("New free deals are waiting for you!")
            .setContentText("Check it out")
            .setContentIntent(activityPendingIntent)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(1, notification)
    }
}