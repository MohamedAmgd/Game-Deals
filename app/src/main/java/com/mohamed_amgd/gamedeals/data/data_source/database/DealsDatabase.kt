package com.mohamed_amgd.gamedeals.data.data_source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mohamed_amgd.gamedeals.domain.models.Deal

@Database(entities = [Deal::class], version = 1, exportSchema = false)
abstract class DealsDatabase : RoomDatabase() {
    abstract fun dealDao(): DealDao

    companion object {
        const val DATABASE_NAME = "deals_db"
    }
}
