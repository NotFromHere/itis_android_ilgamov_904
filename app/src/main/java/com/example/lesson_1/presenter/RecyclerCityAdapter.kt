package com.example.lesson_1.presenter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.lesson_1.data.api.json.City

class RecyclerCityAdapter(): ListAdapter<City, CityViewHolder>(object :
    DiffUtil.ItemCallback<City>() {
    override fun areItemsTheSame(oldItem: City, newItem: City) = oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: City, newItem: City) = oldItem == newItem

}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CityViewHolder.create(parent)

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}