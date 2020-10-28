package com.example.lesson_1

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.list_add_note_dialog.view.*

class ListAddNoteDialogFragment(
    private val fragmentList: FragmentList
): DialogFragment() {

    val bundle = Bundle()

    companion object{
        val NOTE_TITLE = "key_Title"
        val NOTE_DESC = "key_Desc"
        val NOTE_ID = "key_ID"
        val POSITION = "key_Pos"
    }

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
            val noteId: Int = fragmentList.noteRep.size + 1
            val position: Int
            if (dialogView.descriptionInput.text.isNotEmpty() && dialogView.titleInputText.text?.isNotEmpty() == true){
                noteTitle = dialogView.descriptionInput.text.toString()
                noteDescription = dialogView.titleInputText.text.toString()
                if(dialogView.posInput.text.toString().isEmpty()){
                    position = fragmentList.noteRep.size
                } else {
                    position = dialogView.posInput.text.toString().toInt()
                }
                bundle.putInt(NOTE_ID, noteId)
                bundle.putInt(POSITION, position)
                bundle.putString(NOTE_TITLE, noteTitle)
                bundle.putString(NOTE_DESC, noteDescription)
            } else {
                Toast.makeText(this.context, "Введите все данные", Toast.LENGTH_SHORT).show()
            }
            dismiss()
        }
        return builder.create()
    }

    override fun onDestroyView() {
        if(!bundle.isEmpty){
            fragmentList.uploadNewList(bundle)
        }
        super.onDestroyView()
    }

}