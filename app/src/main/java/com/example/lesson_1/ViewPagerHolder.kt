package com.example.lesson_1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_pager_image.*

class ViewPagerHolder(
    override val containerView: View
): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(imageInt: Int){
        imageView.setImageResource(imageInt)
    }

    companion object{
        fun create(parent: ViewGroup): ViewPagerHolder{
            return ViewPagerHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.view_pager_image, parent, false))
        }
    }
}