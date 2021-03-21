package com.example.lesson_1.activity


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.SearchView

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.lesson_1.R
import com.example.lesson_1.api.ApiFactory
import com.example.lesson_1.json.CityID
import com.example.lesson_1.recycler.RecyclerCityAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {

    private var latitude: Double = 30.0
    private var longitude: Double = 20.0
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
            ApiFactory.weatherAPI.getCities(lat, lon, cnt).run {
                rvCityAdapter.submitList(list)
            }
        }
    }

    private fun searchCity() {
        search_main.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val gson = Gson()
                val typeToke: Type = object : TypeToken<List<CityID>>() {}.type
                val cityList: List<CityID> = gson.fromJson(
                    JsonReader(InputStreamReader(assets.open("city.list.json"))),
                    typeToke
                )
                var flag = false
                var cityID: Int = -1
                cityList.forEach {
                    if (it.name.equals(query)) {
                        flag = true
                        cityID = it.id
                        return@forEach
                    }
                }
                if (flag) {
                    Intent(this@MainActivity, WeatherActivity::class.java).run {
                        putExtra(WeatherActivity.CITY_ID, cityID)
                        startActivity(this)
                    }
                } else {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Город $query не найден",
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

}




