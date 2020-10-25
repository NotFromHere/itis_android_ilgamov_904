package com.example.lesson_1

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.description_city.*


class ActivityCityDescription: AppCompatActivity() {

    private var cityID: Int = -1

    companion object{
        const val CITY_ID = "cityID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.description_city)
        cityID = intent.getIntExtra(CITY_ID, -1)
        loadData()
    }

    private fun loadData(){
        sourceCity.cities.forEach{
            if(it.id == cityID){
                findViewById<TextView>(R.id.cityTitle).text = it.name
                findViewById<TextView>(R.id.cityDescription).text = it.cityDescription
                findViewById<ImageView>(R.id.cityImage).setImageResource(it.imageRes)

                return
            }

        }

    }
}