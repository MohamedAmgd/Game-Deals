package com.mohamed_amgd.gamedeals.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Deal(

    @SerializedName("name")
    val name: String,

    @SerializedName("old_price")
    val oldPrice: String,

    @SerializedName("new_price")
    val newPrice: String,

    @SerializedName("discount_percentage")
    val discountPercentage: String,

    @SerializedName("expiry_date")
    val expiryDate: String,

    @SerializedName("image_url")
    val imageURL: String,

    @PrimaryKey
    @SerializedName("discount_url")
    val discountURL: String,

    @SerializedName("is_historical_low")
    val isHistoricalLow: Boolean
)