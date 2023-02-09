package com.egp.makaroniyeh.services

import com.egp.makaroniyeh.model.Data
import com.egp.makaroniyeh.model.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APIInterfaces {
    @GET("products/cat/1")
    fun getBestSeller(): Call<Product?>?

    @GET("products/cat/2")
    fun getPopular(): Call<Product?>?

    @GET("products")
    fun getNewArrivals(): Call<Product?>?

    @GET("products/cat/{id}")
    fun getByCatId(@Path("id") id : Int): Call<Product?>?
}