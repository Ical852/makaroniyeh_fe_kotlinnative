package com.egp.makaroniyeh.model


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("meta")
    val meta: Meta
)