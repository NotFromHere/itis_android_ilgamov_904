package com.example.lesson_1


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_list.*

class FragmentList(): Fragment() {

    var adapter: NoteListAdapter? = null

    var noteRep = noteRepository.noteList


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        adapter = NoteListAdapter {
            noteRep.remove(it)
            adapter?.submitList(noteRep)
        }
        recycle_view.adapter = adapter
        recycle_view.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        adapter?.submitList(noteRep)
        fab.setOnClickListener{
            val listAddNoteDialogFragment = ListAddNoteDialogFragment(this)
            fragmentManager?.let { it1 -> listAddNoteDialogFragment.show(it1, "ListAdd") }

        }
        super.onActivityCreated(savedInstanceState)
    }

    fun uploadNewList(bundle: Bundle){
        val position = bundle.getInt(ListAddNoteDialogFragment.POSITION)
        val note = Note(
            bundle.getInt(ListAddNoteDialogFragment.NOTE_ID),
            bundle.getString(ListAddNoteDialogFragment.NOTE_TITLE)!!,
            bundle.getString(ListAddNoteDialogFragment.NOTE_DESC)!!
        )
        if(position == 0 || position >= noteRep.size) {
            noteRep.add(note)
        } else {
            noteRep.add(position - 1, note)
        }
        adapter?.submitList(noteRep)
    }

}