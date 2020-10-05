package com.example.lesson_1

import android.content.Intent
import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.registration_activity.*

class RegistrationActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_activity)

        registration_next.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("NAME", registration_name.text.toString())
            intent.putExtra("DATA", "Registration_date: " + registration_data.text.toString())
            intent.putExtra("EMAIL", registration_email.text.toString())
            startActivity(intent)
        })
    }

}