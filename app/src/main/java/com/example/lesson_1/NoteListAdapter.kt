package com.example.lesson_1

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class NoteListAdapter(
    val itemClickLambda: (Note) -> Unit
) : ListAdapter<Note, NoteViewHolder>(
    /*anon class*/
    object: DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem.equals(newItem)

        override fun getChangePayload(oldItem: Note, newItem: Note): Any? {
            val bundle = Bundle()
            if(oldItem.title != newItem.title) {
                bundle.putString("KEY_TITLE", newItem.title)
            }
            if(oldItem.desc != newItem.desc) {
                bundle.putString("KEY_DESC", newItem.desc)
            }
            return if (bundle.isEmpty) null else bundle
        }
    }) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder = NoteViewHolder.create(parent, itemClickLambda)

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: NoteViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if(payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else(payloads[0] as? Bundle).also {
            holder.bundleBind(it)
        }
    }

    override fun submitList(list: MutableList<Note>?) {
        super.submitList(if (list != null) ArrayList(list) else list)
    }
}