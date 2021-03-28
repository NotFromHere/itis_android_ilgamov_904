package com.example.lesson_1.presenter.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.lesson_1.R
import com.example.lesson_1.data.impl.WeatherRepositoryImpl
import com.example.lesson_1.data.api.ApiFactory
import com.example.lesson_1.domain.usecase.GetWeatherUseCase
import com.example.lesson_1.presenter.AppDatabase
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherActivity : AppCompatActivity() {

    companion object{
        val CITY_ID = "KEY_CID"
    }

    private val getWeatherUseCase: GetWeatherUseCase by lazy {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        )
            .allowMainThreadQueries() //ТУТ ТОЖЕ ПОЗЯЗЯ
            .fallbackToDestructiveMigration()
            .build()
        ApiFactory.weatherAPI.let {
            WeatherRepositoryImpl(it, db.weatherDao()).let {
                GetWeatherUseCase(it)
            }
        }
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
            val weather = getWeatherUseCase.getWeather(cityID)
            tv_city_name.text = weather.cityName
            tv_temp.text = tv_temp.text.toString() + weather.temp.toInt() + "°C"
            tv_temp_fill.text =
                tv_temp_fill.text.toString() + " " +  weather.tempFeel.toInt() + "°C"
            tv_cloudy.text = tv_cloudy.text.toString() + weather.cloudy
            tv_humidity.text = tv_humidity.text.toString() + weather.humidity + "%"
            val windDeg = weather.wind
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
            tv_pressure.text = tv_pressure.text.toString() + weather.pressure + " мм рт. ст."
        }
    }
}