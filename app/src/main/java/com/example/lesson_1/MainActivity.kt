package com.example.lesson_1




import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {

    var name = "Name"
    var email = "Email"
    var data = "Registration_date: Jan 2020"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        name = intent.getStringExtra("NAME")?: "Name"
        email = intent.getStringExtra("EMAIL")?: "Email"
        data = intent.getStringExtra("DATA")?: "Registration_date: Jan 2020"

        profile_data.setText(data)
        profile_name.setText(name)
        profile_login.setText(email)

    }

    fun renameFun(view: View){
        val nameField: TextView = findViewById(R.id.profile_name)

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val input = EditText(this)

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