package com.example.lesson_1.activity

import WeatherResponse
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lesson_1.R
import com.example.lesson_1.api.OpenWeatherAPI
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WeatherActivity : AppCompatActivity() {

    private val API_BASE_URL: String = "http://api.openweathermap.org/data/2.5/"
    private val client by lazy { OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build() }
    private val retrofit by lazy { Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build() }
    private val openWeatherApi by lazy { retrofit.create(OpenWeatherAPI::class.java) }

    companion object{
        val CITY_NAME = "KEY_CN"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        val cityName = intent.getStringExtra(CITY_NAME)
        tv_city_name.text = cityName

        CoroutineScope(Dispatchers.Main).launch {
            val wResponse: WeatherResponse = openWeatherApi.getWeather(cityName)
            tv_temp.text = tv_temp.text.toString() + wResponse.main.temp.toInt() + "°C"
            tv_temp_fill.text = tv_temp_fill.text.toString() + wResponse.main.feelsLike.toInt() + "°C"
            tv_cloudy.text = tv_cloudy.text.toString() + wResponse.weather.get(0).description
            tv_humidity.text = tv_humidity.text.toString() + wResponse.main.humidity + "%"
            val windDeg = wResponse.wind.deg
            when{
                windDeg < 45 -> tv_wind.text = tv_wind.text.toString() + "Север"
                windDeg < 90 -> tv_wind.text = tv_wind.text.toString() + "Северо-Восток"
                windDeg < 135 -> tv_wind.text = tv_wind.text.toString() + "Восток"
                windDeg < 180 -> tv_wind.text = tv_wind.text.toString() + "Юго-Восток"
                windDeg < 225 -> tv_wind.text = tv_wind.text.toString() + "Юг"
                windDeg < 270 -> tv_wind.text = tv_wind.text.toString() + "Юго-Запад"
                windDeg < 315 -> tv_wind.text = tv_wind.text.toString() + "Запад"
                windDeg < 360 -> tv_wind.text = tv_wind.text.toString() + "Северо-Запад"
                else -> tv_wind.text = tv_wind.text.toString() + "Север"
            }
            tv_pressure.text = tv_pressure.text.toString() + wResponse.main.pressure + " мм рт. ст."
        }
    }
}