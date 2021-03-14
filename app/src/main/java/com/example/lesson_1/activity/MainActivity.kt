package com.example.lesson_1.activity


import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.SearchView

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.lesson_1.R
import com.example.lesson_1.api.OpenWeatherAPI
import com.example.lesson_1.recycler.City
import com.example.lesson_1.recycler.RecyclerCityAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val API_BASE_URL: String = "http://api.openweathermap.org/data/2.5/"
    private var latitude: Double = 30.0
    private var longitude: Double = 20.0
    private val client by lazy { OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build() }
    private val retrofit by lazy { Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build() }
    private val openWeatherApi by lazy { retrofit.create(OpenWeatherAPI::class.java) }
    private val rvCityAdapter by lazy { RecyclerCityAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_main.adapter = rvCityAdapter // load adapter

        searchCity()
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION), 1)
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            } else {
                FusedLocationProviderClient(this).lastLocation.addOnCompleteListener {
                        if (it.result != null){
                            this@MainActivity.latitude = it.result.latitude
                            this@MainActivity.longitude = it.result.longitude
                        }
                        getCitiesAround(latitude, longitude, 20)

                }

            }
    }

    private fun getCitiesAround(lat: Double, lon: Double, cnt: Int){
        CoroutineScope(Dispatchers.Main).launch {
            openWeatherApi.getCities(lat, lon, cnt).run {
                rvCityAdapter.submitList(list)
            }
        }
    }

    private fun searchCity() {
        search_main.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        openWeatherApi.getWeather(query)
                        Intent(this@MainActivity, WeatherActivity::class.java).run {
                            putExtra(WeatherActivity.CITY_NAME, query)
                            startActivity(this)
                        }
                    } catch (e: HttpException) {
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            "Город $query не найден",
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //nothing
                return true
            }

        })
    }

}




