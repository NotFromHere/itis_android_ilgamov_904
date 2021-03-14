package com.example.lesson_1.api

import CitiesAroundResponse
import WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherAPI {
    @GET("weather?appid=e99f8d6bf92009340a1da7398a99b3c0&units=metric&lang=ru")
    suspend fun getWeather(@Query("q") cityName: String?): WeatherResponse

    @GET("find?appid=e99f8d6bf92009340a1da7398a99b3c0&units=metric&lang=ru")
    suspend fun getCities(@Query("lat") lat: Double,
                          @Query("lon") lon: Double,
                          @Query("cnt") cnt: Int): CitiesAroundResponse
}