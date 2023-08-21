package com.mohamed_amgd.gamedeals.domain.repositories

import com.mohamed_amgd.gamedeals.domain.models.Deal

interface DealRepository {

    suspend fun getHotDeals(): List<Deal>

    suspend fun getFreeDeals(): List<Deal>

    suspend fun getDealsByTitle(title: String): List<Deal>

    suspend fun isFreeDealsChanged(): Boolean
}