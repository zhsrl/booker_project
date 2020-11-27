package com.e.booker.view.ui.fragments.managerFragment

import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.e.booker.R
import com.e.booker.view.ui.fragments.multichoiseFragment.GenreMultiChoiseDialogFragment
import com.e.booker.viewmodel.manageFragmentViewModel.PanelViewModel
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class PanelFragment : Fragment(), GenreMultiChoiseDialogFragment.OnMultiChoiseListener {

    var PDF_REQUEST_CODE = 1
    var IMG_REQUEST_CODE = 2
    var AUDIO_REQUEST_CODE = 3

    private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference

    private lateinit var pdfURI: Uri
    private lateinit var imgURI: Uri
    private lateinit var audioURI: Uri


    companion object {
        fun newInstance() = PanelFragment()
    }

    private lateinit var addImage: ConstraintLayout
    private lateinit var addFile: ConstraintLayout
    private lateinit var bookImage: ImageView
    private lateinit var imagePath: TextView
    private lateinit var filePath: TextView
    private lateinit var bookFormat: MaterialButton
    private lateinit var bookGenre: MaterialButton
    private lateinit var addBook: Button

    private lateinit var viewModel: PanelViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.panel_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PanelViewModel::class.java)

        init()

        bookGenre.setOnClickListener {
            val genreDialog = GenreMultiChoiseDialogFragment()
            genreDialog.isCancelable = false
            fragmentManager?.let { it1 -> genreDialog.show(it1, "Select Genre") }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PDF_REQUEST_CODE && resultCode == RESULT_OK){
            pdfURI = data?.data!!
        }else if(requestCode == IMG_REQUEST_CODE && resultCode == RESULT_OK){
            imgURI = data?.data!!
        }else if(requestCode == AUDIO_REQUEST_CODE && resultCode == RESULT_OK){
            audioURI = data?.data!!
        }
    }

    fun getFileExtension(uri: Uri): String? {
        val contResolver: ContentResolver = context!!.contentResolver
        val mimeTypeMap: MimeTypeMap = MimeTypeMap.getSingleton()

        return mimeTypeMap.getExtensionFromMimeType(contResolver.getType(uri))
    }

    override fun positiveButtonPressed(list: Array<String>, selectedItem: ArrayList<String>) {
        Toast.makeText(context!!.applicationContext, "POS", Toast.LENGTH_SHORT).show()
    }

    override fun negativeButtonPressed() {
        Toast.makeText(context!!.applicationContext, "NEG", Toast.LENGTH_SHORT).show()
    }



    private fun init(){
        addImage = view!!.findViewById(R.id.addImageLAY)
        imagePath = view!!.findViewById(R.id.manage_image_dir_pathTV)
        bookImage = view!!.findViewById(R.id.manageBookIMG)

        addFile = view!!.findViewById(R.id.addFileLAY)
        filePath = view!!.findViewById(R.id.manage_file_dir_pathTV)

        bookFormat = view!!.findViewById(R.id.book_formatBTN)
        bookGenre = view!!.findViewById(R.id.book_genreBTN)

        addBook = view!!.findViewById(R.id.addBookBTN)

    }

}