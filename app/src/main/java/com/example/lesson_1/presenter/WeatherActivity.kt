package com.example.lesson_1.presenter

import Main
import Weather
import WeatherResponse
import Wind
import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lesson_1.R
import com.example.lesson_1.data.api.ApiFactory
import com.example.lesson_1.data.api.WeatherRepositoryImpl
import com.example.lesson_1.domain.GetWeatherUseCase
import com.example.lesson_1.sqllitetest.MySQLiteOpenHelper
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class WeatherActivity : AppCompatActivity() {

    private val getWeatherAroundUseCase = ApiFactory.weatherAPI.let {
        WeatherRepositoryImpl(it).let {
            GetWeatherUseCase(it)
        }
    }

    private lateinit var mySQLiteOpenHelper: MySQLiteOpenHelper
    private lateinit var database: SQLiteDatabase;

    companion object{
        val CITY_ID = "KEY_CID"
        const val DATABASE_NAME = "MY_DB"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        dbExec()
        val cityID = intent.getIntExtra(CITY_ID, -1)
        setupWeather(cityID)
    }

    private fun dbExec() {
        mySQLiteOpenHelper = MySQLiteOpenHelper(this, MainActivity.DATABASE_NAME, null, 2)
        database = try {
            mySQLiteOpenHelper.writableDatabase
        } catch (e: SQLiteException){
            mySQLiteOpenHelper.readableDatabase
        }
        mySQLiteOpenHelper.onOpen(database)
    }

    @SuppressLint("SetTextI18n")
    private fun setupWeather(cityID: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            var wResponse: WeatherResponse
                try {
                    wResponse = getWeatherAroundUseCase.getWeather(cityID)
                    val contentValues = ContentValues()
                    contentValues.put("W_CITY_ID", cityID)
                    contentValues.put("W_CITY_NAME", wResponse.name)
                    contentValues.put("W_TEMP", wResponse.main?.temp?.toInt())
                    contentValues.put("W_TEMP_FILL", wResponse.main?.feelsLike?.toInt())
                    contentValues.put("W_CLOUDY", wResponse.weather?.get(0)?.description )
                    contentValues.put("W_HUMIDITY", wResponse.main?.humidity)
                    contentValues.put("W_WIND", wResponse.wind?.deg)
                    contentValues.put("W_PRESSURE", wResponse.main?.pressure)
                    database.insert("WEATHER", null, contentValues)
                } catch (e: UnknownHostException) {
                    database.query("WEATHER", null, "W_CITY_ID = $cityID", null, null, null, null).let{
                        it.moveToNext()
                        val wName: String = it.getString(it.getColumnIndex("W_CITY_NAME"))
                        val wTemp: Double = it.getDouble(it.getColumnIndex("W_TEMP"))
                        val wTempFill: Double = it.getDouble(it.getColumnIndex("W_TEMP_FILL"))
                        val wDesc: String = it.getString(it.getColumnIndex("W_CLOUDY"))
                        val wHumidity: Int = it.getInt(it.getColumnIndex("W_HUMIDITY"))
                        val wDeg: Int = it.getInt(it.getColumnIndex("W_WIND"))
                        val wPressure: Int = it.getInt(it.getColumnIndex("W_PRESSURE"))
                        val wMain: Main = Main(wTempFill, wHumidity, wPressure, wTemp, null, null)
                        val wWind: Wind = Wind(wDeg, null)
                        val weather: Weather = Weather(wDesc, null, null, null)
                        val wWeather: List<Weather> = arrayListOf(weather)
                        wResponse = WeatherResponse(null, null, null, null, null, null, wMain, wName, null, null, null, wWeather, wWind)
                    }
                    if (wResponse == null){
                        wResponse = WeatherResponse(null, null, null, null, null, null, null, null, null, null, null, null, null)
                    }

                }

            tv_city_name.text = wResponse.name
            tv_temp.text = tv_temp.text.toString() + wResponse.main?.temp?.toInt() + "°C"
            tv_temp_fill.text =
                tv_temp_fill.text.toString() + " " +  wResponse.main?.feelsLike?.toInt() + "°C"
            tv_cloudy.text = tv_cloudy.text.toString() + (wResponse.weather?.get(0)?.description ?: 0)
            tv_humidity.text = tv_humidity.text.toString() + (wResponse.main?.humidity ?: 0) + "%"
            val windDeg = wResponse.wind?.deg
            if (windDeg != null) {
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
            }
            tv_pressure.text = tv_pressure.text.toString() + (wResponse.main?.pressure ?: 0) + " мм рт. ст."
        }
    }
}