package com.example.lesson_1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.card_view.*

class CardViewHolder(
    override val containerView: View

) : RecyclerView.ViewHolder(containerView), LayoutContainer {


    fun bind(cardView: CardView){
        viewPagerImage.adapter = ViewPagerAdapter(cardView.imageList)
        titleViewPager.text = cardView.title
        descriptionViewPager.text = cardView.description
    }

    companion object{
        fun create(parent: ViewGroup): CardViewHolder{
            return CardViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.card_view, parent, false))
        }
    }
}

