package com.example.lesson_1

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.list_add_note_dialog.*

class FragmentList: Fragment() {

    private var adapter: NoteListAdapter? = null

    var noteRep = noteRepository.noteList


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        adapter = NoteListAdapter {}
        recycle_view.adapter = adapter
        adapter?.submitList(noteRep)
        fab.setOnClickListener{
            val listAddNoteDialogFragment = ListAddNoteDialogFragment()
            fragmentManager?.let { it1 -> listAddNoteDialogFragment.show(it1, "ListAdd") }
            this.adapter!!.submitList(noteRep)
        }
        super.onActivityCreated(savedInstanceState)
    }

}