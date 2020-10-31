package com.example.lesson_1

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ViewPagerAdapter(
    var imageList: ArrayList<Int>
): RecyclerView.Adapter<ViewPagerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder = ViewPagerHolder.create(parent)

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) = holder.bind(imageList.get(position))

    override fun getItemCount(): Int = imageList.size
}
