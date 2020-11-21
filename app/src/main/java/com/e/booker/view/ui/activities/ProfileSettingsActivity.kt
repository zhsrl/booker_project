package com.e.booker.view.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.e.booker.R

class ProfileSettingsActivity : AppCompatActivity() {

    private lateinit var goBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)

        initAll()

        goBack.setOnClickListener{
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext, HomeActivity::class.java)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }

    fun initAll(){
        goBack = findViewById(R.id.goBackIV)
    }
}