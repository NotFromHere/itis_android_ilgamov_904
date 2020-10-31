package com.example.lesson_1

import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class ListCardViewAdapter(): ListAdapter<CardView, CardViewHolder>(object: DiffUtil.ItemCallback<CardView>(){

    override fun areItemsTheSame(oldItem: CardView, newItem: CardView): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CardView, newItem: CardView): Boolean = oldItem.equals(newItem)

}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder = CardViewHolder.create(parent)

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}