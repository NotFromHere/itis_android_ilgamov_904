package com.example.lesson_1


import android.os.Bundle
import android.view.View
import android.widget.SearchView

import androidx.appcompat.app.AppCompatActivity
import com.example.lesson_1.api.OpenWeatherAPI
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    val API_BASE_URL: String = "http://api.openweathermap.org/data/2.5/"
    lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = OkHttpClient.Builder().build()
        retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        search_main.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val openWeatherApi: OpenWeatherAPI = retrofit.create(OpenWeatherAPI::class.java)
                val code: Int = openWeatherApi.getWeather(query).cod

                if(code.equals(404)){
                    Snackbar.make(View(this@MainActivity), "Error + $query", Snackbar.LENGTH_LONG)
                        .show();
                    return false
                } else {
                    Snackbar.make(View(this@MainActivity), "Ok + $query", Snackbar.LENGTH_LONG)
                        .show();
                    return true
                }
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //nothing
                return true
            }

        })

    }
}




