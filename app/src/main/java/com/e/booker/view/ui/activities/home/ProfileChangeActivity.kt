package com.e.booker.view.ui.activities.home

import android.graphics.BlurMaskFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.e.booker.R
import de.hdodenhof.circleimageview.CircleImageView

class ProfileChangeActivity : AppCompatActivity() {

    private lateinit var userImage: CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_change)

        initAll()

        Glide.with(applicationContext)
                .load(R.drawable.user)
                .into(userImage)

    }

    fun initAll(){
        userImage = findViewById(R.id.profChangeUserImage)

    }
}