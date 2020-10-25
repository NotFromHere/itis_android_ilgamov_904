package com.example.lesson_1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.rv_item_city.*


class VH_City(
    override val containerView: View,
    private val itemClick: (City) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(city: City){
        itemCityName.text = city.name
        itemView.setOnClickListener(){
            itemClick(city)
        }
    }

    companion object{
        fun create(parent: ViewGroup, itemClick: (City) -> Unit): VH_City =
            VH_City(LayoutInflater.from(parent.context).
            inflate(R.layout.rv_item_city, parent, false), itemClick)
    }
}