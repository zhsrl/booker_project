package com.e.booker.view.ui.activities.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.e.booker.R
import com.e.booker.view.ui.activities.auth.LoginAcitivity
import com.e.booker.viewmodel.ViewModelProviderFactory
import com.e.booker.viewmodel.activityviewmodel.ProflieSettingsViewModel
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.math.log

class ProfileSettingsActivity : AppCompatActivity() {

    //Toolbar elements
    private lateinit var goBack: ImageView
    private lateinit var profileChange: ImageView

    //Main Page Elements
    private lateinit var userImage: CircleImageView
    private lateinit var userName: TextView
    private lateinit var userSurname: TextView
    private lateinit var userEmail: TextView

    //Help Buttons
    private lateinit var help: TextView
    private lateinit var aboutBooker: TextView
    private lateinit var logOut: TextView

    //ViewModel
    private lateinit var profSettingsViewModel: ProflieSettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)

        initAll()

        val vmProviderFactory = ViewModelProviderFactory(context = this)

        profSettingsViewModel = ViewModelProvider(this, vmProviderFactory).get(ProflieSettingsViewModel::class.java)

        goBack.setOnClickListener{
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        }


        profileChange.setOnClickListener{
            val intent = Intent(applicationContext, ProfileChangeActivity::class.java)
            startActivity(intent)
        }

        logOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(applicationContext, LoginAcitivity::class.java)
            startActivity(intent)
            finish()
        }

        profSettingsViewModel.showData(userName, userSurname, userEmail)

        aboutBooker.setOnClickListener {
            profSettingsViewModel.showAboutBooker()
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
        profileChange = findViewById(R.id.profileChangeIV)

        //Main page
        userImage = findViewById(R.id.profileSetUserImageIV)
        userName = findViewById(R.id.profSettingsName)
        userSurname = findViewById(R.id.profSettingsSurname)
        userEmail = findViewById(R.id.profSettingsEmail)

        help = findViewById(R.id.helpTV)
        aboutBooker = findViewById(R.id.aboutBookerTV)
        logOut = findViewById(R.id.logOutTV)
    }
}