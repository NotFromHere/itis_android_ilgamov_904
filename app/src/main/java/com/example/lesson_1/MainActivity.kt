package com.example.lesson_1


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*Load Fragment when app launched*/
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, FragmentTextView())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()



        /*BottomNavigation item_listeners*/
        btm_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.emptyFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, FragmentTextView())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit()
                    true
                }
                R.id.listFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, FragmentList())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit()
                    true
                }
                R.id.cardFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, FragmentCardView())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit()
                    true
                } else -> false
            }
        }

        /*Fix BottomNavigation*/
        btm_navigation.setOnNavigationItemReselectedListener {  }
    }
}