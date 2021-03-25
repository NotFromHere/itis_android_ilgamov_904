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

