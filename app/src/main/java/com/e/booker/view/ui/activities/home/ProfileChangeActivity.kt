package com.e.booker.view.ui.activities.home

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.e.booker.R
import com.e.booker.model.User
import com.e.booker.viewmodel.ViewModelProviderFactory
import com.e.booker.viewmodel.activityviewmodel.ProfileChangeViewModel
import com.e.booker.viewmodel.fragmentviewmodel.ChangePasswordViewModel
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.Exception

class ProfileChangeActivity : AppCompatActivity() {

    private lateinit var userImage: CircleImageView
    private lateinit var userName: EditText
    private lateinit var userSurname: EditText

    private lateinit var updateData: ImageView
    private lateinit var uploadUserImage: ImageView

    private lateinit var profChangeViewModel: ProfileChangeViewModel
    val currentUser = FirebaseAuth.getInstance().currentUser!!.uid

    private lateinit var imgURI: Uri

    private lateinit var userImageStorageReference: StorageReference
    private lateinit var userImageDatabaseReference: DatabaseReference

    val IMG_REQUEST_CODE:Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_change)

        val providerFactory = ViewModelProviderFactory(context = this)
        profChangeViewModel = ViewModelProvider(this, providerFactory).get(ProfileChangeViewModel::class.java)



        initAll()

        profChangeViewModel.showCurrentData(userName,userSurname)

        updateData.setOnClickListener{
            profChangeViewModel.changeData(userName, userSurname)
            uploadProfileUserImage()
        }

        uploadUserImage.setOnClickListener{
            Toast.makeText(applicationContext, "Choose User Image", Toast.LENGTH_SHORT).show()
            loadIntent()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMG_REQUEST_CODE && resultCode == RESULT_OK){
            imgURI = data?.data!!
        }

    }

    fun loadIntent(){
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT).also { imageIntent ->
            startActivityForResult(imageIntent,IMG_REQUEST_CODE)
        }

    }

    fun getFileExtension(uri: Uri): String? {
        val contResolver: ContentResolver = contentResolver
        val mimeTypeMap: MimeTypeMap = MimeTypeMap.getSingleton()

        return mimeTypeMap.getExtensionFromMimeType(contResolver.getType(uri))
    }

    fun uploadProfileUserImage(){
        userImageDatabaseReference = FirebaseDatabase
            .getInstance()
            .reference
            .child("Users")
            .child(currentUser)
            .child("image")

        userImageStorageReference = FirebaseStorage
            .getInstance()
            .getReference("Users")

        val sUserRef = userImageStorageReference.child(currentUser + "." + getFileExtension(imgURI))

        sUserRef.putFile(imgURI)
                .addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot> {
                    override fun onSuccess(p0: UploadTask.TaskSnapshot?) {
                        Toast.makeText(applicationContext, "Upload Image success!", Toast.LENGTH_SHORT).show()
                        sUserRef.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
                            override fun onSuccess(p0: Uri?) {

                                val imageURL = p0.toString()

                                userImageDatabaseReference.setValue(imageURL)
                            }
                        })
                    }
                })
    }

    fun initAll(){
        userImage = findViewById(R.id.profChangeUserImage)
        userName = findViewById(R.id.profChangeNameET)
        userSurname = findViewById(R.id.profChangeSurnameET)

        updateData = findViewById(R.id.profileChangeIV)
        uploadUserImage = findViewById(R.id.chooseUserImageIV)

    }
}
