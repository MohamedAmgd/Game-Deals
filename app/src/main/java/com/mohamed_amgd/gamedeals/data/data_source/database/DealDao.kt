package com.mohamed_amgd.gamedeals.data.data_source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mohamed_amgd.gamedeals.domain.models.Deal

@Dao
interface DealDao {

    @Query("SELECT * FROM deal")
    suspend fun getAll(): List<Deal>

    @Insert
    suspend fun insertAll(deals: List<Deal>)

    @Query("DELETE FROM deal")
    suspend fun deleteAll()
}