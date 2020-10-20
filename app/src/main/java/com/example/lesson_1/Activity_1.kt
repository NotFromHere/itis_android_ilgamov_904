package com.example.lesson_1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_one.*

class Activity_1: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one)

        view_action.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)

            intent.setData(Uri.parse("http://developer.alexanderklimov.ru"))
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(Intent.createChooser(intent, "choose"), 1)
            }
            else
                Log.d("123123", "error")

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == 0)
            Toast.makeText(this, "NOT RESULT", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "OK RESULT, ReqC:" + requestCode.toString(), Toast.LENGTH_SHORT).show()

    }
}