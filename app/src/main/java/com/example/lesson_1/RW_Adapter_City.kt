package com.example.lesson_1

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RW_Adapter_City(
    val data: List<City>,
    private val itemClick: (City) -> Unit
) : RecyclerView.Adapter<VH_City>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH_City = VH_City.create(parent, itemClick)

    override fun onBindViewHolder(holder: VH_City, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}