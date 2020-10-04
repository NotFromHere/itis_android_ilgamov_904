package com.example.lesson_1


import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun renameFun(view: View){
        var nameField: TextView = findViewById(R.id.textView5)

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val input: EditText = EditText(this)

        builder.setView(input)
            .setTitle("Изменить имя")
            .setPositiveButton("Ok",
            DialogInterface.OnClickListener { dialog, whichButton ->
                val string: String = input.text.toString()
                nameField.setText(string)
            })

        builder.show()
    }
}