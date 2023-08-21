package com.mohamed_amgd.gamedeals

import android.app.Application
import androidx.room.Room
import com.mohamed_amgd.gamedeals.data.data_source.api.GameDealsApi
import com.mohamed_amgd.gamedeals.data.data_source.database.DealDao
import com.mohamed_amgd.gamedeals.data.data_source.database.DealsDatabase
import com.mohamed_amgd.gamedeals.data.repositories.DealRepositoryImpl
import com.mohamed_amgd.gamedeals.domain.repositories.DealRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object GameDealsAppModule {
    private const val dealsApiUrl = "https://game-deals.azurewebsites.net/"

    @Provides
    @Singleton
    fun provideDealRepository(
        dealsApi: GameDealsApi,
        dealDao: DealDao,
    ): DealRepository {
        return DealRepositoryImpl(dealsApi, dealDao)
    }

    @Provides
    @Singleton
    fun provideGameDealsApi(): GameDealsApi {
        return Retrofit
            .Builder()
            .baseUrl(dealsApiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GameDealsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDealDao(application: Application): DealDao {
        return Room
            .databaseBuilder(
                application,
                DealsDatabase::class.java,
                DealsDatabase.DATABASE_NAME
            )
            .build()
            .dealDao()
    }

}