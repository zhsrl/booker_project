package com.e.booker.view.ui.fragments.bottomsheet

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.e.booker.R
import com.e.booker.viewmodel.ViewModelProviderFactory
import com.e.booker.viewmodel.fragmentviewmodel.ProfileChangeViewModel
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import de.hdodenhof.circleimageview.CircleImageView

class ProfileChangeBottomSheet: BottomSheetDialogFragment() {

    private lateinit var currentImage: CircleImageView
    private lateinit var setImage: ImageView
    private lateinit var editName: EditText
    private lateinit var editSurname: EditText

    private lateinit var update: MaterialButton

    val currentUser = FirebaseAuth.getInstance().currentUser!!.uid

    private lateinit var imgURI: Uri

    val IMG_REQUEST_CODE = 2

    private lateinit var userImageStorageReference: StorageReference
    private lateinit var userImageDatabaseReference: DatabaseReference


    private lateinit var profileChangeViewModel: ProfileChangeViewModel

    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.profile_change_bottom_sheet, null, false)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetStyle)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val providerFactory = ViewModelProviderFactory(context = context!!)
        profileChangeViewModel = ViewModelProvider(this, providerFactory).get(ProfileChangeViewModel::class.java)

        initAll()

        profileChangeViewModel.showCurrentData(editName, editSurname, currentImage)

        setImage.setOnClickListener{
            loadIntent()

        }

        update.setOnClickListener {
            profileChangeViewModel.changeData(editName, editSurname)
            uploadImage()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMG_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            imgURI = data?.data!!
        }

    }

    private fun getFileExtension(uri: Uri): String? {
        val contResolver: ContentResolver = context!!.contentResolver
        val mimeTypeMap: MimeTypeMap = MimeTypeMap.getSingleton()

        return mimeTypeMap.getExtensionFromMimeType(contResolver.getType(uri))
    }

    private fun loadIntent() {
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT).also { imageIntent ->
            startActivityForResult(imageIntent,IMG_REQUEST_CODE)
        }

    }

    private fun uploadImage(){
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
                        Toast.makeText(context, "Upload Image success!", Toast.LENGTH_SHORT).show()
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
        currentImage = view!!.findViewById(R.id.bsh_profChangeUserImage)
        setImage = view!!.findViewById(R.id.bsh_chooseUserImageIV)
        editName = view!!.findViewById(R.id.bsh_profChangeNameET)
        editSurname = view!!.findViewById(R.id.bsh_profChangeSurnameET)
        update = view!!.findViewById(R.id.bsh_updateDataBTN)
    }
}