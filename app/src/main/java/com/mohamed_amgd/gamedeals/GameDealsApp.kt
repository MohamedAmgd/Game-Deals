package com.mohamed_amgd.gamedeals

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.mohamed_amgd.gamedeals.presentation.CheckFreeGamesWorker
import com.mohamed_amgd.gamedeals.presentation.FreeGamesNotificationService
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class GameDealsApp : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        createFreeGamesNotificationChannel()
        runCheckFreeGamesWorker()
    }

    private fun runCheckFreeGamesWorker() {
        CheckFreeGamesWorker.schedule(applicationContext)
    }

    private fun createFreeGamesNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                FreeGamesNotificationService.FREE_GAMES_CHANNEL_ID,
                FreeGamesNotificationService.FREE_GAMES_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = FreeGamesNotificationService.FREE_GAMES_CHANNEL_DESCRIPTION
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(Log.VERBOSE)
            .build()

}