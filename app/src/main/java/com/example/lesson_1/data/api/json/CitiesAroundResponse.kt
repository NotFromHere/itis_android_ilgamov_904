package com.example.lesson_1.data.api.json


import com.google.gson.annotations.SerializedName

data class CitiesAroundResponse(
    @SerializedName("cod")
    var cod: String,
    @SerializedName("count")
    var count: Int,
    @SerializedName("list")
    var list: List<City>,
    @SerializedName("message")
    var message: String
)

data class City(
    @SerializedName("coord")
    var coord: Coord,
    @SerializedName("dt")
    var dt: Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("main")
    var main: Main,
    @SerializedName("name")
    var name: String,
    @SerializedName("rain")
    var rain: Any,
    @SerializedName("snow")
    var snow: Any,
    @SerializedName("sys")
    var sys: Sys,
    @SerializedName("weather")
    var weather: List<Weather>,
    @SerializedName("wind")
    var wind: Wind
)

