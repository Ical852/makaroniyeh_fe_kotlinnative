package com.egp.makaroniyeh.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("category")
    val category: Category,
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("desc")
    val desc: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int
)