package com.example.myapplication

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
interface ToiletService {
    @GET("toilettes")
    fun getAllToilets(): Call<List<Toilet>>
    @POST("toilettes")
    fun addToilet(@Body toilet: Toilet): Call<Toilet>
}