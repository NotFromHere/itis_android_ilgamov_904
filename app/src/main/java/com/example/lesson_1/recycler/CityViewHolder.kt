package com.example.lesson_1.recycler

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson_1.R
import com.example.lesson_1.activity.WeatherActivity
import com.example.lesson_1.json.City
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.viewholder_city.view.*

class CityViewHolder(override val containerView: View):
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    @SuppressLint("SetTextI18n")
    fun bind(city: City){
        containerView.tv_cityName.text = city.name
        containerView.tv_cityTemp.text = city.main.temp.toString() + "°C"
        setTempColor(city)
        containerView.setOnClickListener {
            Intent(containerView.context, WeatherActivity::class.java).also {
                it.putExtra(WeatherActivity.CITY_ID, city.id)
                containerView.context.startActivity(it)
            }
        }
    }

    private fun setTempColor(city: City) {
        when {
            city.main.temp < -20.0 -> {
                containerView.tv_cityTemp.setTextColor(
                    ContextCompat.getColor(
                        containerView.context,
                        R.color.tempMinus20
                    )
                )
            }
            city.main.temp < -10.0 -> {
                containerView.tv_cityTemp.setTextColor(
                    ContextCompat.getColor(
                        containerView.context,
                        R.color.tempMinus10
                    )
                )
            }
            city.main.temp < 0 -> {
                containerView.tv_cityTemp.setTextColor(
                    ContextCompat.getColor(
                        containerView.context,
                        R.color.tempZero
                    )
                )
            }
            city.main.temp < 10.0 -> {
                containerView.tv_cityTemp.setTextColor(
                    ContextCompat.getColor(
                        containerView.context,
                        R.color.tempPlus10
                    )
                )
            }
            city.main.temp < 20.0 -> {
                containerView.tv_cityTemp.setTextColor(
                    ContextCompat.getColor(
                        containerView.context,
                        R.color.tempPlus20
                    )
                )
            }
            else -> {
                containerView.tv_cityTemp.setTextColor(
                    ContextCompat.getColor(
                        containerView.context,
                        R.color.tempPlusPlus
                    )
                )
            }
        }
    }

    companion object{
        fun create(parent: ViewGroup)= CityViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_city, parent, false)
        )
    }
}