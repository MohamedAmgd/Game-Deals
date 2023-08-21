package com.mohamed_amgd.gamedeals.presentation

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.mohamed_amgd.gamedeals.domain.repositories.DealRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

@HiltWorker
class CheckFreeGamesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val dealsRepository: DealRepository
) : CoroutineWorker(
    context,
    workerParams
) {
    companion object {
        const val TAG = "CheckFreeGamesWorker"
        private const val INTERVAL_MINUTES = 15L

        fun schedule(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val freeGamesCheckRequest =
                PeriodicWorkRequestBuilder<CheckFreeGamesWorker>(
                    INTERVAL_MINUTES, TimeUnit.MINUTES
                )
                    .setConstraints(constraints)
                    .addTag(TAG)
                    .build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                TAG,
                ExistingPeriodicWorkPolicy.REPLACE,
                freeGamesCheckRequest
            )

        }
    }

    override suspend fun doWork(): Result {
        Log.d(TAG, "doWork: started")
        return try {
            val isFreeDealsChanged = dealsRepository.isFreeDealsChanged()
            if (isFreeDealsChanged)
                FreeGamesNotificationService(applicationContext).showNotification()
            val state = if (isFreeDealsChanged) "new games" else "nothing changed"
            Result.success()
        } catch (exception: SocketTimeoutException) {
            Result.retry()
        } catch (exception: Exception) {
            Result.failure()
        }
    }
}