package com.example.lesson_1


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE


import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView1.setOnClickListener{
            imageView1.setImageResource(R.drawable.icon_botomnav_active)
            imageView2.setImageResource(R.drawable.icon_botomnav)
            imageView3.setImageResource(R.drawable.icon_botomnav)
            imageView4.setImageResource(R.drawable.icon_botomnav)
            imageView5.setImageResource(R.drawable.icon_botomnav)
            supportFragmentManager.beginTransaction().setTransition(TRANSIT_FRAGMENT_FADE).replace(R.id.fragment_container, Fragment_One()).commit()

        }
        imageView2.setOnClickListener{
            imageView2.setImageResource(R.drawable.icon_botomnav_active)
            imageView1.setImageResource(R.drawable.icon_botomnav)
            imageView3.setImageResource(R.drawable.icon_botomnav)
            imageView4.setImageResource(R.drawable.icon_botomnav)
            imageView5.setImageResource(R.drawable.icon_botomnav)
            supportFragmentManager.beginTransaction().setTransition(TRANSIT_FRAGMENT_FADE).replace(R.id.fragment_container, Fragment_Two()).commit()

        }
        imageView3.setOnClickListener{
            imageView3.setImageResource(R.drawable.icon_botomnav_active)
            imageView2.setImageResource(R.drawable.icon_botomnav)
            imageView1.setImageResource(R.drawable.icon_botomnav)
            imageView4.setImageResource(R.drawable.icon_botomnav)
            imageView5.setImageResource(R.drawable.icon_botomnav)
            supportFragmentManager.beginTransaction().setTransition(TRANSIT_FRAGMENT_FADE).replace(R.id.fragment_container, Fragment_Three()).commit()

        }
        imageView4.setOnClickListener{
            imageView4.setImageResource(R.drawable.icon_botomnav_active)
            imageView2.setImageResource(R.drawable.icon_botomnav)
            imageView3.setImageResource(R.drawable.icon_botomnav)
            imageView1.setImageResource(R.drawable.icon_botomnav)
            imageView5.setImageResource(R.drawable.icon_botomnav)
            supportFragmentManager.beginTransaction().setTransition(TRANSIT_FRAGMENT_FADE).replace(R.id.fragment_container, Fragment_Four()).commit()

        }
        imageView5.setOnClickListener{
            imageView5.setImageResource(R.drawable.icon_botomnav_active)
            imageView2.setImageResource(R.drawable.icon_botomnav)
            imageView3.setImageResource(R.drawable.icon_botomnav)
            imageView4.setImageResource(R.drawable.icon_botomnav)
            imageView1.setImageResource(R.drawable.icon_botomnav)
            supportFragmentManager.beginTransaction().setTransition(TRANSIT_FRAGMENT_FADE).replace(R.id.fragment_container, Fragment_Five()).commit()

        }
    }
}