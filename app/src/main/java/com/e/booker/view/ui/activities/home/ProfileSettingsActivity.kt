package com.e.booker.view.ui.activities.home

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.e.booker.R
import com.e.booker.view.ui.fragments.bottomsheet.BottomSheetDialog
import com.e.booker.view.ui.activities.auth.LoginAcitivity
import com.e.booker.view.ui.fragments.bottomsheet.ChangePasswordBottomSheet
import com.e.booker.view.ui.fragments.bottomsheet.ProfileChangeBottomSheet
import com.e.booker.viewmodel.ViewModelProviderFactory
import com.e.booker.viewmodel.activityviewmodel.ProflieSettingsViewModel
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text

class ProfileSettingsActivity : AppCompatActivity() {

    //Toolbar elements
    private lateinit var goBack: ImageView

    //Main Page Elements
    private lateinit var userImage: CircleImageView
    private lateinit var userName: TextView
    private lateinit var userSurname: TextView
    private lateinit var userEmail: TextView
    private lateinit var progressBar: ProgressBar

    //Bottom Dialog
    private var bottomDialog: BottomSheetDialog = BottomSheetDialog()
    private var changeBottomDialog: ChangePasswordBottomSheet = ChangePasswordBottomSheet()
    private var changeDataBottomDialog: ProfileChangeBottomSheet = ProfileChangeBottomSheet()

    //Help Buttons
    private lateinit var help: TextView
    private lateinit var changeData: TextView
    private lateinit var changePassword: TextView
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

        progressBar.visibility = View.VISIBLE
        profSettingsViewModel.liveData.observe(this, Observer{ result ->
            when(result){
                is ProflieSettingsViewModel.State.HideLoading -> {
                    progressBar.visibility = View.GONE
                }
                is ProflieSettingsViewModel.State.ShowLoading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is ProflieSettingsViewModel.State.Result -> {
                    result.email = userEmail
                    result.name = userName
                    result.surname = userSurname
                    result.profileImage = userImage
                    progressBar.visibility = View.GONE
                }
            }
        })
        profSettingsViewModel.showData(userName, userSurname, userEmail, userImage)

        goBack.setOnClickListener{
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        }

        logOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(applicationContext, LoginAcitivity::class.java)
            startActivity(intent)
            finish()
        }

        aboutBooker.setOnClickListener {
            bottomDialog.show(supportFragmentManager, "AboutBooker_TAG")
        }

        changePassword.setOnClickListener {
            changeBottomDialog.show(supportFragmentManager, "ChangePassword_TAG")
        }

        changeData.setOnClickListener {
            changeDataBottomDialog.show(supportFragmentManager,"ChangeData_TAG")
        }

        userImage.setOnClickListener{
            Toast.makeText(applicationContext, "Image clicked!", Toast.LENGTH_SHORT).show()
        }

    }

    fun initAll(){
        goBack = findViewById(R.id.goBackIV)

        //Main page
        userImage = findViewById(R.id.profileSetUserImageIV)
        userName = findViewById(R.id.profSettingsName)
        userSurname = findViewById(R.id.profSettingsSurname)
        userEmail = findViewById(R.id.profSettingsEmail)
        progressBar = findViewById(R.id.progressBar)

        help = findViewById(R.id.helpTV)
        changeData = findViewById(R.id.changeDataTV)
        changePassword = findViewById(R.id.changePasswordTV)
        aboutBooker = findViewById(R.id.aboutBookerTV)
        logOut = findViewById(R.id.logOutTV)
    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext, HomeActivity::class.java)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }
}