package com.example.lesson_1.presenter

import WeatherResponse
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lesson_1.R
import com.example.lesson_1.data.api.ApiFactory
import com.example.lesson_1.data.api.WeatherRepositoryImpl
import com.example.lesson_1.domain.GetWeatherUseCase
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherActivity : AppCompatActivity() {

    private val getWeatherAroundUseCase = ApiFactory.weatherAPI.let {
        WeatherRepositoryImpl(it).let {
            GetWeatherUseCase(it)
        }
    }

    companion object{
        val CITY_ID = "KEY_CID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        val cityID = intent.getIntExtra(CITY_ID, -1)
        setupWeather(cityID)
    }

    @SuppressLint("SetTextI18n")
    private fun setupWeather(cityID: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val wResponse: WeatherResponse = getWeatherAroundUseCase.getWeather(cityID)
            tv_city_name.text = wResponse.name
            tv_temp.text = tv_temp.text.toString() + wResponse.main.temp?.toInt() + "°C"
            tv_temp_fill.text =
                tv_temp_fill.text.toString() + " " +  wResponse.main.feelsLike?.toInt() + "°C"
            tv_cloudy.text = tv_cloudy.text.toString() + wResponse.weather.get(0).description
            tv_humidity.text = tv_humidity.text.toString() + wResponse.main.humidity + "%"
            val windDeg = wResponse.wind.deg
            when {
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