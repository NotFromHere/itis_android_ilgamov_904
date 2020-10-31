package com.example.lesson_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.recycle_item.*

class NoteViewHolder(
    override val containerView: View,
    val itemClickLambda: (Note) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {



    fun bind(note: Note){

        title.text = note.title
        description.text = note.desc

        delete.setOnClickListener{ itemClickLambda(note) }
    }

    fun bundleBind(bundle: Bundle?){
        if (bundle != null) {
            if(bundle.containsKey("KEY_TITLE")){
                title.text = bundle.getString("KEY_TITLE")
            }
            if(bundle.containsKey("KEY_DESC")){
                description.text = bundle.getString("KEY_DESC")
            }
        }

    }

    companion object{
        fun create(parent: ViewGroup, itemClickLambda: (Note) -> Unit): NoteViewHolder{
            return NoteViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.recycle_item, parent, false), itemClickLambda)
        }
    }
}