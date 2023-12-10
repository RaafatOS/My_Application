package com.example.myapplication

import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ToiletService {
    @GET("toilettes")
    fun getAllToilets(): Call<List<Toilet>>
    @POST("toilettes")
    fun addToilet(@Body toilet: Toilet): Call<Toilet>
    @PUT("toilettes/{id}")
    fun updateToilet(@Path("id") id: String): Call<JSONObject>
    @GET("toilettes?favorites=1")
    fun getFavToilets(): Call<List<Toilet>>

}