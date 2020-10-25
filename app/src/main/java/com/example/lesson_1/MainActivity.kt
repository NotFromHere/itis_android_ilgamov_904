package com.example.lesson_1


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_cities.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_cities)



        rv_city.adapter = RW_Adapter_City(sourceCity.cities) {
            val intent = Intent(this, ActivityCityDescription::class.java)
            intent.putExtra(ActivityCityDescription.CITY_ID, it.id)
            startActivity(intent)
        }

        val itemDecoration: RecyclerView.ItemDecoration =
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)

        (itemDecoration as DividerItemDecoration).setDrawable(resources.getDrawable(R.drawable.divider_drawable))

        rv_city.addItemDecoration(itemDecoration)

    }
}