package com.e.booker.view.ui.fragments.managerFragment

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.DialogInterface
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

class PanelFragment : Fragment(), GenreMultiChoiseDialogFragment.OnMultiChoiseListener, AdapterView.OnItemSelectedListener {

    var PDF_REQUEST_CODE = 101
    var IMG_REQUEST_CODE = 202
    var AUDIO_REQUEST_CODE = 303

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
    private lateinit var spinner: Spinner
    private lateinit var spinner2: Spinner

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
//            val genreDialog = GenreMultiChoiseDialogFragment()
//            genreDialog.isCancelable = false
//            fragmentManager?.let { it1 -> genreDialog.show(it1, "Select Genre") }
            val list: Array<String>
            list = resources.getStringArray(R.array.genre_items)
            val builder: AlertDialog.Builder = AlertDialog.Builder(this.context)
            builder.setTitle("Select genre")

            builder.setSingleChoiceItems(list, -1) { dialog, which ->
                Toast.makeText(context!!.applicationContext, list[which], Toast.LENGTH_SHORT).show()
            }
            builder.setNeutralButton("Cancel",){ dialog, which ->
                dialog.dismiss()
            }
            builder.setPositiveButton("OK", object: DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog!!.dismiss()
                }
            })
            val alertDialog = builder.create()

            alertDialog.setOnShowListener(object: DialogInterface.OnShowListener{
                @SuppressLint("ResourceAsColor")
                override fun onShow(dialog: DialogInterface?) {
                    alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(R.color.mainColor)
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.mainColor)
                }
            })

            alertDialog.show()
        }

        spinner.onItemSelectedListener = this
        spinner2.onItemSelectedListener = this


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
        spinner = view!!.findViewById(R.id.testSpinner)
        spinner2 = view!!.findViewById(R.id.test2Spinner)

    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if(parent!!.selectedItem == "Audio Book"){
            Toast.makeText(context!!.applicationContext, "This is audio Rel file", Toast.LENGTH_SHORT).show()
        }else if(parent.selectedItem == "PDF Book"){
            Toast.makeText(context!!.applicationContext, "This is pdf file.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}