package com.example.lesson_1.presenter


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lesson_1.R
import com.example.lesson_1.data.api.ApiFactory
import com.example.lesson_1.data.api.WeatherRepositoryImpl
import com.example.lesson_1.domain.FindCitiesAroundUseCase
import com.example.lesson_1.domain.entity.CityDomain
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.*
import java.lang.reflect.Type


class MainActivity : AppCompatActivity() {

    private var latitude: Double = 30.0
    private var longitude: Double = 20.0
    private val rvCityAdapter by lazy { RecyclerCityAdapter() }

    private val findCitiesAroundUseCase: FindCitiesAroundUseCase by lazy {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        ApiFactory.weatherAPI.let {
            WeatherRepositoryImpl(it, db.weatherDao()).let {
                FindCitiesAroundUseCase(it)
            }
        }
    }

    companion object{
        private const val DATABASE_NAME = "mydb"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv_main.adapter = rvCityAdapter
        searchCity()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        FusedLocationProviderClient(this).lastLocation.addOnCompleteListener {
                        if (it.result != null){
                            this@MainActivity.latitude = it.result.latitude
                            this@MainActivity.longitude = it.result.longitude
                        }
                        getCitiesAround(latitude, longitude, 20)
                }
    }

    private fun getCitiesAround(lat: Double, lon: Double, cnt: Int) {
        lifecycleScope.launch {
                findCitiesAroundUseCase.getCities(lat, lon, cnt).run {
                    rvCityAdapter.submitList(this)
                }
        }
    }

    private fun searchCity() {
        search_main.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                lifecycleScope.launch {
                    try {
                        val cityID = findCitiesAroundUseCase.getCityId(query.toString())
                        Intent(this@MainActivity, WeatherActivity::class.java).run {
                            putExtra(WeatherActivity.CITY_ID, cityID)
                            startActivity(this)
                        }
                    } catch (e: HttpException){
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
                return true
            }

        })
    }

    private fun createRoomDbInstance(): RoomDatabase{
        return Room.databaseBuilder(
            this,
            RoomDatabase::class.java,
            DATABASE_NAME
        )
            .createFromFile(File("path"))
            .createFromAsset("database/mydb.db")
            .build()
    }
}




