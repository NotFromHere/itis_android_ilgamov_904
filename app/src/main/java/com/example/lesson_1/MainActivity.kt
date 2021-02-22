package com.example.lesson_1


import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search_main.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                //req
                val code: String = "number"
                if(code.equals("404")){
                    //Snack-bad
                    return false
                } else {
                    //Snack-ok
                    return true
                }
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //nothing
                return true
            }

        })

    }
}




