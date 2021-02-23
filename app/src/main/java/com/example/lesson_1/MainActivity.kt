package com.example.lesson_1


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.SearchView

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.lesson_1.api.OpenWeatherAPI
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val API_BASE_URL: String = "http://api.openweathermap.org/data/2.5/"
    lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var permission = getPermissionsStatus()

        if (permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf (Manifest.permission.INTERNET), 1)
        }

        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(client)

            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val openWeatherApi: OpenWeatherAPI = retrofit.create(OpenWeatherAPI::class.java)

        search_main.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                var code: Int = -1

                CoroutineScope(Dispatchers.IO).launch {
                 code = openWeatherApi.getWeather(query).cod
                }

                return if(code == 404){
                    Snackbar.make(findViewById(android.R.id.content), "Error + $query", Snackbar.LENGTH_LONG)
                        .show();
                    false
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Ok + $query", Snackbar.LENGTH_LONG)
                        .show();
                    true
                }
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //nothing
                return true
            }

        })

    }

    private fun getPermissionsStatus() =
        ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);

}




