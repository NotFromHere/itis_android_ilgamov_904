package com.example.lesson_1.presenter


import Main
import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.widget.SearchView

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.example.lesson_1.R
import com.example.lesson_1.data.api.ApiFactory
import com.example.lesson_1.data.api.WeatherRepositoryImpl
import com.example.lesson_1.data.api.json.City
import com.example.lesson_1.data.api.json.CityID
import com.example.lesson_1.domain.FindCitiesAroundUseCase
import com.example.lesson_1.sqllitetest.MySQLiteOpenHelper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import java.io.*
import java.lang.Exception
import java.lang.reflect.Type
import java.net.UnknownHostException

class MainActivity : AppCompatActivity() {

    private var latitude: Double = 30.0
    private var longitude: Double = 20.0
    private val rvCityAdapter by lazy { RecyclerCityAdapter() }
    private val findCitiesAroundUseCase = ApiFactory.weatherAPI.let {
            WeatherRepositoryImpl(it).let {
                FindCitiesAroundUseCase(it)
            }
    }
    private lateinit var mySQLiteOpenHelper: MySQLiteOpenHelper
    private lateinit var database: SQLiteDatabase;


    companion object{
        const val DATABASE_NAME = "MY_DB"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv_main.adapter = rvCityAdapter
        searchCity()
        dbExec()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), 1
            )
            return
        } else {
            FusedLocationProviderClient(this).lastLocation.addOnCompleteListener {
                if (it.result != null) {
                    this@MainActivity.latitude = it.result.latitude
                    this@MainActivity.longitude = it.result.longitude
                }
                getCitiesAround(latitude, longitude, 20)
            }
        }
    }

    private fun dbExec() {
        mySQLiteOpenHelper = MySQLiteOpenHelper(this, DATABASE_NAME, null, 2)
        database = try {
            mySQLiteOpenHelper.writableDatabase
        } catch (e: SQLiteException){
            mySQLiteOpenHelper.readableDatabase
        }
        mySQLiteOpenHelper.onOpen(database)
    }

    private fun getCitiesAround(lat: Double, lon: Double, cnt: Int) {
        lifecycleScope.launch {
            try {
                findCitiesAroundUseCase.getCities(lat, lon, cnt).run {
                    database.delete("CITY_AROUND", null, null)
                    rvCityAdapter.submitList(this.list)
                    this.list.forEach {
                        val contentValues = ContentValues()
                        contentValues.put(BaseColumns._ID, it.id)
                        contentValues.put("CITY_NAME", it.name)
                        contentValues.put("COLUMN_TEMP", it.main?.temp)
                        database.insert("CITY_AROUND", null, contentValues)
                    }
                }
            } catch(e: UnknownHostException){
                val list = arrayListOf<City>()
                database.query("CITY_AROUND", null, null, null, null, null, null).let {
                    it.moveToFirst()
                    while(!it.isAfterLast){
                        val id: Int = it.getInt(it.getColumnIndex(BaseColumns._ID))
                        val name: String = it.getString(it.getColumnIndex("CITY_NAME"))
                        val temp: Double = it.getDouble(it.getColumnIndex("COLUMN_TEMP"))
                        val main = Main(null, null, null, temp, null, null)
                        val city = City(null, null, null, id, main, name, null, null, null, null, null)
                        list.add(city)
                        it.moveToNext()
                    }
                    if(list.isEmpty()){
                        Log.d("123123123", "gg")
                    }
                    if(database.isOpen){
                        Log.d("db_connection", "ok")
                    }
                    rvCityAdapter.submitList(list)
                }
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




