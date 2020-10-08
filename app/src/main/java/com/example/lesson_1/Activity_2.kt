package com.example.lesson_1

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_one.*
import kotlinx.android.synthetic.main.activity_second.*
import java.net.URI

class Activity_2: AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val imageUri =  intent.getParcelableExtra<Parcelable?>(Intent.EXTRA_STREAM) as Uri?
        image_intent.setImageURI(imageUri)

    }
}