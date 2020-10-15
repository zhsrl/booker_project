package com.e.booker.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.os.HandlerCompat.postDelayed
import com.e.booker.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var SPLASH_TIME_OUT: Long = 4000


        var handler = Handler()



        handler.postDelayed(Runnable {
            kotlin.run {
                val intent = Intent(applicationContext, RegistrationActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right)
                finish()
            }

        }, SPLASH_TIME_OUT)
    }
}