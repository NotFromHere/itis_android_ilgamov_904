package com.example.lesson_1.presenter.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.room.Room
import com.example.lesson_1.R
import com.example.lesson_1.data.api.ApiFactory
import com.example.lesson_1.data.impl.WeatherRepositoryImpl
import com.example.lesson_1.domain.usecase.GetWeatherUseCase
import com.example.lesson_1.presenter.AppDatabase
import com.example.lesson_1.presenter.moxy.WeatherPresenter
import com.example.lesson_1.presenter.moxy.WeatherView
import com.example.lesson_1.presenter.other.DataBaseInstance
import kotlinx.android.synthetic.main.activity_weather.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class WeatherActivity : MvpAppCompatActivity(), WeatherView {

    companion object {
        val CITY_ID = "KEY_CID"
    }

    @InjectPresenter
    lateinit var weatherPresenter: WeatherPresenter

    @ProvidePresenter
    fun providePresenter(): WeatherPresenter = initPresenter()

    private val getWeatherUseCase: GetWeatherUseCase by lazy {
        val db = DataBaseInstance.getDataBase(this)
        ApiFactory.weatherAPI.let {
            WeatherRepositoryImpl(it, db.weatherDao()).let {
                GetWeatherUseCase(it)
            }
        }
    }

    private fun initPresenter(): WeatherPresenter = WeatherPresenter(getWeatherUseCase)

    private val cityID: Int by lazy { intent.getIntExtra(CITY_ID, -1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        weatherPresenter.setupWeather(cityID)
    }


    override fun setupCityName(cityName: String) {
        tv_city_name.text = cityName
    }

    @SuppressLint("SetTextI18n")
    override fun setupTemp(temp: String) {
        tv_temp.text = tv_temp.text.toString() + temp
    }

    @SuppressLint("SetTextI18n")
    override fun setupTempFeel(tempFeel: String) {
        tv_temp_fill.text =
            tv_temp_fill.text.toString() + " " + tempFeel
    }

    @SuppressLint("SetTextI18n")
    override fun setupCloudy(cloudy: String) {
        tv_cloudy.text = tv_cloudy.text.toString() + cloudy
    }

    @SuppressLint("SetTextI18n")
    override fun setupHumidity(humidity: String) {
        tv_humidity.text = tv_humidity.text.toString() + humidity
    }

    @SuppressLint("SetTextI18n")
    override fun setupWind(wind: String) {
        tv_wind.text = tv_wind.text.toString() + wind
    }

    @SuppressLint("SetTextI18n")
    override fun setupPressure(pressure: String) {
        tv_pressure.text = tv_pressure.text.toString() + pressure
    }

    override fun progressOn() {
        progress.visibility = View.VISIBLE
    }

    override fun progressOff() {
        progress.visibility = View.INVISIBLE
    }

}