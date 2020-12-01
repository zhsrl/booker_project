package com.e.booker.viewmodel.manageFragmentViewModel

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.MimeTypeMap
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.e.booker.model.FilePDF
import com.e.booker.view.ui.activities.adminpanel.ManagePanelActivity
import com.e.booker.view.ui.fragments.managerFragment.PanelFragment
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class PanelViewModel(val context: Context) : ViewModel() {

//    private var activity = ManagePanelActivity()
    private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference

    lateinit var pdfUri: Uri
    lateinit var imgUri: Uri
    lateinit var audioUri: Uri


    fun initActivityResult(requestCode: Int,
                           resultCode: Int,
                           data: Intent?,
                           pdfCode: Int,
                           imgCode: Int,
                           audioCode: Int,
                           ){
        if(requestCode == pdfCode && resultCode == Activity.RESULT_OK){
            pdfUri = data?.data!!
        }else if(requestCode == imgCode && resultCode == Activity.RESULT_OK){
            imgUri = data?.data!!
        }else if(requestCode == audioCode && resultCode == Activity.RESULT_OK){
            audioUri = data?.data!!
        }

    }

    fun getFileExtension(uri: Uri): String? {
        val contResolver: ContentResolver = context.contentResolver
        val mimeTypeMap: MimeTypeMap = MimeTypeMap.getSingleton()

        return mimeTypeMap.getExtensionFromMimeType(contResolver.getType(uri))
    }

    fun uploadData(type: TextView, genre: TextView, title: EditText, author: EditText, desc: EditText){
        if(type.text == "Audio Book" && genre.text == "Religion"){
            Toast.makeText(context, "Audio - Religion", Toast.LENGTH_SHORT).show()

        }else if(type.text == "Audio Book" && genre.text == "Psychology"){
            Toast.makeText(context, "Audio - Psychology", Toast.LENGTH_SHORT).show()

        }else if(type.text == "Audio Book" && genre.text == "Business"){
            Toast.makeText(context, "Audio - Business", Toast.LENGTH_SHORT).show()

        }else if(type.text == "Audio Book" && genre.text == "History"){
            Toast.makeText(context, "Audio - History", Toast.LENGTH_SHORT).show()

        }else if(type.text == "Audio Book" && genre.text == "Literature"){
            Toast.makeText(context, "Audio - Literature", Toast.LENGTH_SHORT).show()

        }else if(type.text == "Audio Book" && genre.text == "Science"){
            Toast.makeText(context, "Audio - Science", Toast.LENGTH_SHORT).show()

        }else if(type.text == "Audio Book" && genre.text == "Motivation"){
            Toast.makeText(context, "Audio - Motivation", Toast.LENGTH_SHORT).show()

        }else if(type.text == "PDF Book" && genre.text == "Religion"){
            Toast.makeText(context, "PDF Book - Religion", Toast.LENGTH_SHORT).show()
            storageReference = FirebaseStorage
                    .getInstance()
                    .getReference("Uploads")
                    .child("PDFBooks")
                    .child("Religion")

            databaseReference = FirebaseDatabase
                    .getInstance()
                    .getReference("Uploads")
                    .child("PDFBooks")
                    .child("Religion")

            val sRef = storageReference.child(title.text.toString().trim() + "." + getFileExtension(pdfUri))

            sRef.putFile(pdfUri)
                    .addOnSuccessListener(object: OnSuccessListener<UploadTask.TaskSnapshot>{
                        override fun onSuccess(p0: UploadTask.TaskSnapshot?) {
                            val sRef2 = storageReference.child(title.text.toString().trim() + "." + getFileExtension(imgUri))

                            sRef2.putFile(imgUri)
                                    .addOnSuccessListener(object: OnSuccessListener<UploadTask.TaskSnapshot>{
                                        override fun onSuccess(p0: UploadTask.TaskSnapshot?) {
                                            Toast.makeText(context, "Upload Success!", Toast.LENGTH_SHORT).show()

                                            sRef.downloadUrl.addOnSuccessListener(object: OnSuccessListener<Uri>{
                                                override fun onSuccess(downloadPdf: Uri?) {
                                                    sRef2.downloadUrl.addOnSuccessListener(object: OnSuccessListener<Uri>{
                                                        override fun onSuccess(downloadImg: Uri?) {
                                                            val filePDF = downloadPdf.toString()
                                                            val fileIMG = downloadImg.toString()
                                                            val fileTitle = title.text.toString().trim()
                                                            val fileAuthor = author.text.toString().trim()
                                                            val fileDescription = desc.text.toString().trim()

                                                            val file = FilePDF(filePDF,fileIMG, fileTitle, fileAuthor, fileDescription)

                                                            val uploadID: String = databaseReference.push().key!!
                                                            databaseReference.child(uploadID).setValue(file)

                                                        }
                                                    })
                                                }
                                            })
                                        }
                                    })
                        }
                    })

        }else if(type.text == "PDF Book" && genre.text == "Psychology"){
            Toast.makeText(context, "Audio - Psychology", Toast.LENGTH_SHORT).show()

            storageReference = FirebaseStorage
                    .getInstance()
                    .getReference("Uploads")
                    .child("PDFBooks")
                    .child("Psychology")
                    .child(title.text.toString().trim())

            databaseReference = FirebaseDatabase
                    .getInstance()
                    .getReference("Uploads")
                    .child("PDFBooks")
                    .child("Psychology")

            val sRef = storageReference.child(title.text.toString().trim() + "." + getFileExtension(pdfUri))

            sRef.putFile(pdfUri)
                    .addOnSuccessListener(object: OnSuccessListener<UploadTask.TaskSnapshot>{
                        override fun onSuccess(p0: UploadTask.TaskSnapshot?) {
                            val sRef2 = storageReference.child(title.text.toString().trim() + "." + getFileExtension(imgUri))

                            sRef2.putFile(imgUri)
                                    .addOnSuccessListener(object: OnSuccessListener<UploadTask.TaskSnapshot>{
                                        override fun onSuccess(p0: UploadTask.TaskSnapshot?) {
                                            Toast.makeText(context, "Upload Success!", Toast.LENGTH_SHORT).show()

                                            sRef.downloadUrl.addOnSuccessListener(object: OnSuccessListener<Uri>{
                                                override fun onSuccess(downloadPdf: Uri?) {
                                                    sRef2.downloadUrl.addOnSuccessListener(object: OnSuccessListener<Uri>{
                                                        override fun onSuccess(downloadImg: Uri?) {
                                                            val filePDF = downloadPdf.toString()
                                                            val fileIMG = downloadImg.toString()
                                                            val fileTitle = title.text.toString().trim()
                                                            val fileAuthor = author.text.toString().trim()
                                                            val fileDescription = desc.text.toString().trim()

                                                            val file = FilePDF(filePDF,fileIMG, fileTitle, fileAuthor, fileDescription)

                                                            val uploadID: String = databaseReference.push().key!!
                                                            databaseReference.child(uploadID).setValue(file)

                                                        }
                                                    })
                                                }
                                            })
                                        }
                                    })
                        }
                    })

        }else if(type.text == "PDF Book" && genre.text == "Business"){
            Toast.makeText(context, "PDF - Business", Toast.LENGTH_SHORT).show()

            storageReference = FirebaseStorage
                    .getInstance()
                    .getReference("Uploads")
                    .child("PDFBooks")
                    .child("Business")
                    .child(title.text.toString().trim())

            databaseReference = FirebaseDatabase
                    .getInstance()
                    .getReference("Uploads")
                    .child("PDFBooks")
                    .child("Business")

            val sRef = storageReference.child(title.text.toString().trim() + "." + getFileExtension(pdfUri))

            sRef.putFile(pdfUri)
                    .addOnSuccessListener(object: OnSuccessListener<UploadTask.TaskSnapshot>{
                        override fun onSuccess(p0: UploadTask.TaskSnapshot?) {
                            val sRef2 = storageReference.child(title.text.toString().trim() + "." + getFileExtension(imgUri))

                            sRef2.putFile(imgUri)
                                    .addOnSuccessListener(object: OnSuccessListener<UploadTask.TaskSnapshot>{
                                        override fun onSuccess(p0: UploadTask.TaskSnapshot?) {
                                            Toast.makeText(context, "Upload Success!", Toast.LENGTH_SHORT).show()

                                            sRef.downloadUrl.addOnSuccessListener(object: OnSuccessListener<Uri>{
                                                override fun onSuccess(downloadPdf: Uri?) {
                                                    sRef2.downloadUrl.addOnSuccessListener(object: OnSuccessListener<Uri>{
                                                        override fun onSuccess(downloadImg: Uri?) {
                                                            val filePDF = downloadPdf.toString()
                                                            val fileIMG = downloadImg.toString()
                                                            val fileTitle = title.text.toString().trim()
                                                            val fileAuthor = author.text.toString().trim()
                                                            val fileDescription = desc.text.toString().trim()

                                                            val file = FilePDF(filePDF,fileIMG, fileTitle, fileAuthor, fileDescription)

                                                            val uploadID: String = databaseReference.push().key!!
                                                            databaseReference.child(uploadID).setValue(file)

                                                        }
                                                    })
                                                }
                                            })
                                        }
                                    })
                        }
                    })

        }else if(type.text == "PDF Book" && genre.text == "History"){
            Toast.makeText(context, "PDF - History", Toast.LENGTH_SHORT).show()

            storageReference = FirebaseStorage
                    .getInstance()
                    .getReference("Uploads")
                    .child("PDFBooks")
                    .child("History")
                    .child(title.text.toString().trim())

            databaseReference = FirebaseDatabase
                    .getInstance()
                    .getReference("Uploads")
                    .child("PDFBooks")
                    .child("History")

            val sRef = storageReference.child(title.text.toString().trim() + "." + getFileExtension(pdfUri))

            sRef.putFile(pdfUri)
                    .addOnSuccessListener(object: OnSuccessListener<UploadTask.TaskSnapshot>{
                        override fun onSuccess(p0: UploadTask.TaskSnapshot?) {
                            val sRef2 = storageReference.child(title.text.toString().trim() + "." + getFileExtension(imgUri))

                            sRef2.putFile(imgUri)
                                    .addOnSuccessListener(object: OnSuccessListener<UploadTask.TaskSnapshot>{
                                        override fun onSuccess(p0: UploadTask.TaskSnapshot?) {
                                            Toast.makeText(context, "Upload Success!", Toast.LENGTH_SHORT).show()

                                            sRef.downloadUrl.addOnSuccessListener(object: OnSuccessListener<Uri>{
                                                override fun onSuccess(downloadPdf: Uri?) {
                                                    sRef2.downloadUrl.addOnSuccessListener(object: OnSuccessListener<Uri>{
                                                        override fun onSuccess(downloadImg: Uri?) {
                                                            val filePDF = downloadPdf.toString()
                                                            val fileIMG = downloadImg.toString()
                                                            val fileTitle = title.text.toString().trim()
                                                            val fileAuthor = author.text.toString().trim()
                                                            val fileDescription = desc.text.toString().trim()

                                                            val file = FilePDF(filePDF,fileIMG, fileTitle, fileAuthor, fileDescription)

                                                            val uploadID: String = databaseReference.push().key!!
                                                            databaseReference.child(uploadID).setValue(file)

                                                        }
                                                    })
                                                }
                                            })
                                        }
                                    })
                        }
                    })

        }else if(type.text == "PDF Book" && genre.text == "Literature"){
            Toast.makeText(context, "PDF - Literature", Toast.LENGTH_SHORT).show()

            storageReference = FirebaseStorage
                    .getInstance()
                    .getReference("Uploads")
                    .child("PDFBooks")
                    .child("Literature")
                    .child(title.text.toString().trim())

            databaseReference = FirebaseDatabase
                    .getInstance()
                    .getReference("Uploads")
                    .child("PDFBooks")
                    .child("Literature")

            val sRef = storageReference.child(title.text.toString().trim() + "." + getFileExtension(pdfUri))

            sRef.putFile(pdfUri)
                    .addOnSuccessListener(object: OnSuccessListener<UploadTask.TaskSnapshot>{
                        override fun onSuccess(p0: UploadTask.TaskSnapshot?) {
                            val sRef2 = storageReference.child(title.text.toString().trim() + "." + getFileExtension(imgUri))

                            sRef2.putFile(imgUri)
                                    .addOnSuccessListener(object: OnSuccessListener<UploadTask.TaskSnapshot>{
                                        override fun onSuccess(p0: UploadTask.TaskSnapshot?) {
                                            Toast.makeText(context, "Upload Success!", Toast.LENGTH_SHORT).show()

                                            sRef.downloadUrl.addOnSuccessListener(object: OnSuccessListener<Uri>{
                                                override fun onSuccess(downloadPdf: Uri?) {
                                                    sRef2.downloadUrl.addOnSuccessListener(object: OnSuccessListener<Uri>{
                                                        override fun onSuccess(downloadImg: Uri?) {
                                                            val filePDF = downloadPdf.toString()
                                                            val fileIMG = downloadImg.toString()
                                                            val fileTitle = title.text.toString().trim()
                                                            val fileAuthor = author.text.toString().trim()
                                                            val fileDescription = desc.text.toString().trim()

                                                            val file = FilePDF(filePDF,fileIMG, fileTitle, fileAuthor, fileDescription)

                                                            val uploadID: String = databaseReference.push().key!!
                                                            databaseReference.child(uploadID).setValue(file)

                                                        }
                                                    })
                                                }
                                            })
                                        }
                                    })
                        }
                    })

        }else if(type.text == "PDF Book" && genre.text == "Science"){
            Toast.makeText(context, "PDF - Science", Toast.LENGTH_SHORT).show()

            storageReference = FirebaseStorage
                    .getInstance()
                    .getReference("Uploads")
                    .child("PDFBooks")
                    .child("Science")
                    .child(title.text.toString().trim())

            databaseReference = FirebaseDatabase
                    .getInstance()
                    .getReference("Uploads")
                    .child("PDFBooks")
                    .child("Science")

            val sRef = storageReference.child(title.text.toString().trim() + "." + getFileExtension(pdfUri))

            sRef.putFile(pdfUri)
                    .addOnSuccessListener(object: OnSuccessListener<UploadTask.TaskSnapshot>{
                        override fun onSuccess(p0: UploadTask.TaskSnapshot?) {
                            val sRef2 = storageReference.child(title.text.toString().trim() + "." + getFileExtension(imgUri))

                            sRef2.putFile(imgUri)
                                    .addOnSuccessListener(object: OnSuccessListener<UploadTask.TaskSnapshot>{
                                        override fun onSuccess(p0: UploadTask.TaskSnapshot?) {
                                            Toast.makeText(context, "Upload Success!", Toast.LENGTH_SHORT).show()

                                            sRef.downloadUrl.addOnSuccessListener(object: OnSuccessListener<Uri>{
                                                override fun onSuccess(downloadPdf: Uri?) {
                                                    sRef2.downloadUrl.addOnSuccessListener(object: OnSuccessListener<Uri>{
                                                        override fun onSuccess(downloadImg: Uri?) {
                                                            val filePDF = downloadPdf.toString()
                                                            val fileIMG = downloadImg.toString()
                                                            val fileTitle = title.text.toString().trim()
                                                            val fileAuthor = author.text.toString().trim()
                                                            val fileDescription = desc.text.toString().trim()

                                                            val file = FilePDF(filePDF,fileIMG, fileTitle, fileAuthor, fileDescription)

                                                            val uploadID: String = databaseReference.push().key!!
                                                            databaseReference.child(uploadID).setValue(file)

                                                        }
                                                    })
                                                }
                                            })
                                        }
                                    })
                        }
                    })

        }else if(type.text == "PDF Book" && genre.text == "Motivation"){
            Toast.makeText(context, "PDF Book - Motivation", Toast.LENGTH_SHORT).show()
            storageReference = FirebaseStorage
                    .getInstance()
                    .getReference("Uploads")
                    .child("PDFBooks")
                    .child("Motivation")
                    .child(title.text.toString().trim())

            databaseReference = FirebaseDatabase
                    .getInstance()
                    .getReference("Uploads")
                    .child("PDFBooks")
                    .child("Motivation")

            val sRef = storageReference.child(title.text.toString().trim() + "." + getFileExtension(pdfUri))

            sRef.putFile(pdfUri)
                    .addOnSuccessListener(object: OnSuccessListener<UploadTask.TaskSnapshot>{
                        override fun onSuccess(p0: UploadTask.TaskSnapshot?) {
                            val sRef2 = storageReference.child(title.text.toString().trim() + "." + getFileExtension(imgUri))

                            sRef2.putFile(imgUri)
                                    .addOnSuccessListener(object: OnSuccessListener<UploadTask.TaskSnapshot>{
                                        override fun onSuccess(p0: UploadTask.TaskSnapshot?) {
                                            Toast.makeText(context, "Upload Success!", Toast.LENGTH_SHORT).show()

                                            sRef.downloadUrl.addOnSuccessListener(object: OnSuccessListener<Uri>{
                                                override fun onSuccess(downloadPdf: Uri?) {
                                                    sRef2.downloadUrl.addOnSuccessListener(object: OnSuccessListener<Uri>{
                                                        override fun onSuccess(downloadImg: Uri?) {
                                                            val filePDF = downloadPdf.toString()
                                                            val fileIMG = downloadImg.toString()
                                                            val fileTitle = title.text.toString().trim()
                                                            val fileAuthor = author.text.toString().trim()
                                                            val fileDescription = desc.text.toString().trim()

                                                            val file = FilePDF(filePDF,fileIMG, fileTitle, fileAuthor, fileDescription)

                                                            val uploadID: String = databaseReference.push().key!!
                                                            databaseReference.child(uploadID).setValue(file)

                                                        }
                                                    })
                                                }
                                            })
                                        }
                                    })
                        }
                    })

        }
    }



}