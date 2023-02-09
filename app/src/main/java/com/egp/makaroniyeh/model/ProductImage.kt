package com.egp.makaroniyeh.model

import com.google.gson.annotations.SerializedName

data class ProductImage(
    @SerializedName("id")
    val id: Int,
    @SerializedName("category_id")
    val product_id: Int,
    @SerializedName("image")
    val image: String,
)
