package com.example.lesson_1

import parentClass
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("Some message")
        val object1 = parentClass("Gh", 123)
        val childObject1 = childClass("GG", 101)

        Log.d("myTag", object1.someFun().toString());
        println("Some message")
    }
}