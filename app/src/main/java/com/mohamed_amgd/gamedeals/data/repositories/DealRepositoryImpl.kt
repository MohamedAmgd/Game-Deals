package com.mohamed_amgd.gamedeals.data.repositories

import com.mohamed_amgd.gamedeals.data.data_source.api.GameDealsApi
import com.mohamed_amgd.gamedeals.data.data_source.database.DealDao
import com.mohamed_amgd.gamedeals.domain.models.Deal
import com.mohamed_amgd.gamedeals.domain.repositories.DealRepository

class DealRepositoryImpl(
    private val remote: GameDealsApi,
    private val local: DealDao
) : DealRepository {

    override suspend fun getHotDeals(): List<Deal> {
        return remote.getAllDeals()
    }

    override suspend fun getFreeDeals(): List<Deal> {
        return remote.getAllDeals(free = true)
    }

    override suspend fun getDealsByTitle(title: String): List<Deal> {
        return remote.getAllDeals(title = title)
    }

    override suspend fun isFreeDealsChanged(): Boolean {
        val oldFreeDealsNames = local.getAll().map { it.name }
        val newFreeDeals = getFreeDeals()
        val newFreeDealsNames = newFreeDeals.map { it.name }
        val newDealsFound = !oldFreeDealsNames.containsAll(newFreeDealsNames)
        if (newDealsFound) {
            local.deleteAll()
            local.insertAll(newFreeDeals)
            return newDealsFound
        }
        return newDealsFound
    }
}