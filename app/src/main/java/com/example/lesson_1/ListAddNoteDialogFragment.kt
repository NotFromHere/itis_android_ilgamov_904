package com.example.lesson_1

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.list_add_note_dialog.*
import kotlinx.android.synthetic.main.list_add_note_dialog.view.*

class ListAddNoteDialogFragment: DialogFragment() {

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder: AlertDialog.Builder = AlertDialog.Builder(getActivity() as AppCompatActivity)
        val dialogView = (getActivity() as AppCompatActivity).layoutInflater.inflate(R.layout.list_add_note_dialog, null)
        builder.setView(dialogView)
        dialogView.close.setOnClickListener{
            dismiss()
        }
        dialogView.sumbit.setOnClickListener{
            val noteTitle: String
            val noteDescription: String
            val noteId: Int = noteRepository.noteList.size + 1
            if (dialogView.descriptionInput.text.isNotEmpty() && dialogView.titleInputText.text?.isNotEmpty() == true){
                noteTitle = dialogView.descriptionInput.text.toString()
                noteDescription = dialogView.titleInputText.text.toString()
            } else {
                Toast.makeText(this.context, "Error", Toast.LENGTH_SHORT).show()
            }
            dismiss()
        }
        return builder.create()
    }
}