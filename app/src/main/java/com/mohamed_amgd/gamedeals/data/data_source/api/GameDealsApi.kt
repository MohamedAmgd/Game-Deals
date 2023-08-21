package com.mohamed_amgd.gamedeals.data.data_source.api

import com.mohamed_amgd.gamedeals.domain.models.Deal
import retrofit2.http.GET
import retrofit2.http.Query

interface GameDealsApi {

    @GET("/")
    suspend fun getAllDeals(
        @Query("free") free: Boolean? = null,
        @Query("page") page: Number? = null,
        @Query("title") title: String? = null
    ): List<Deal>
}