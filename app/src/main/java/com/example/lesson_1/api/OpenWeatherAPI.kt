package com.example.lesson_1.api

import CitiesAroundResponse
import WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherAPI {
    @GET("weather")
    suspend fun getWeather(@Query("id") cityID: Int): WeatherResponse

    @GET("find")
    suspend fun getCities(@Query("lat") lat: Double,
                          @Query("lon") lon: Double,
                          @Query("cnt") cnt: Int): CitiesAroundResponse
}