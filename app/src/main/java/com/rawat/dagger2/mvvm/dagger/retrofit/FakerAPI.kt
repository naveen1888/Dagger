package com.rawat.dagger2.mvvm.dagger.retrofit

import com.rawat.dagger2.mvvm.dagger.models.Product
import retrofit2.Response
import retrofit2.http.GET

interface FakerAPI {

    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("products")
    suspend fun getProductsFlow(): List<Product>

    @GET("products")
    suspend fun getProductsUnit()
}