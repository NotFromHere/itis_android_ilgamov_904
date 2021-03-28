package com.example.lesson_1.presenter.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.lesson_1.domain.entity.CityDomain

class RecyclerCityAdapter(): ListAdapter<CityDomain, CityViewHolder>(object :
    DiffUtil.ItemCallback<CityDomain>() {
    override fun areItemsTheSame(oldItem: CityDomain, newItem: CityDomain) = oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: CityDomain, newItem: CityDomain) = oldItem == newItem

}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CityViewHolder.create(parent)

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}