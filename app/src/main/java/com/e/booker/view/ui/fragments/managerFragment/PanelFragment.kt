package com.e.booker.view.ui.fragments.managerFragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.e.booker.R
import com.e.booker.viewmodel.ViewModelProviderFactory
import com.e.booker.viewmodel.manageFragmentViewModel.PanelViewModel
import com.google.android.material.button.MaterialButton

class PanelFragment : Fragment(){

    var FILE_REQUEST_CODE = 1
    var IMG_REQUEST_CODE = 2

    private lateinit var addImage: ConstraintLayout
    private lateinit var addFile: ConstraintLayout
    private lateinit var bookImage: ImageView
    private lateinit var imagePath: TextView
    private lateinit var filePath: TextView
    private lateinit var bookFormat: MaterialButton
    private lateinit var bookGenre: MaterialButton
    private lateinit var addBook: Button

    private lateinit var fileTitle: EditText
    private lateinit var fileAuthor: EditText
    private lateinit var fileDescription: EditText

    private lateinit var formatFile: TextView
    private lateinit var genreFile: TextView
    private lateinit var viewModel: PanelViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.panel_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val providerFactory = ViewModelProviderFactory(this.context!!)
        viewModel = ViewModelProvider(this, providerFactory).get(PanelViewModel::class.java)

        init()

        bookFormat.setOnClickListener {
            val list: Array<String>
            list = resources.getStringArray(R.array.format_items)
            val builder: AlertDialog.Builder = AlertDialog.Builder(this.context)
            builder.setTitle("Select format")

            builder.setSingleChoiceItems(list, -1){ dialog, which ->
                if(list[which] == "Audio Book"){
                    Toast.makeText(context!!.applicationContext, "AUDIO", Toast.LENGTH_SHORT).show()
                    formatFile.text = "Audio Book"
                }else if(list[which] == "PDF Book"){
                    Toast.makeText(context!!.applicationContext, "PDF", Toast.LENGTH_SHORT).show()
                    formatFile.text = "PDF Book"
                }
                dialog.dismiss()
            }

            val alertDialog = builder.create()

            alertDialog.setOnShowListener(object : DialogInterface.OnShowListener {
                @SuppressLint("ResourceAsColor")
                override fun onShow(dialog: DialogInterface?) {
                    alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(R.color.mainColor)
                }
            })

            alertDialog.show()
        }

        bookGenre.setOnClickListener {
            val list: Array<String>
            list = resources.getStringArray(R.array.genre_items)
            val builder: AlertDialog.Builder = AlertDialog.Builder(this.context)
            builder.setTitle("Select genre")

            builder.setSingleChoiceItems(list, -1) { dialog, which ->
                if (list[which] == "Religion"){
                    Toast.makeText(context!!.applicationContext, "REL", Toast.LENGTH_SHORT).show()
                    genreFile.text = "Religion"
                }
                else if (list[which] == "Psychology"){
                    Toast.makeText(context!!.applicationContext, "REL", Toast.LENGTH_SHORT).show()
                    genreFile.text = "Psychology"
                }
                else if (list[which] == "Business"){
                    Toast.makeText(context!!.applicationContext, "REL", Toast.LENGTH_SHORT).show()
                    genreFile.text = "Business"
                }
                else if (list[which] == "History"){
                    Toast.makeText(context!!.applicationContext, "REL", Toast.LENGTH_SHORT).show()
                    genreFile.text = "History"
                }
                else if (list[which] == "Literature"){
                    Toast.makeText(context!!.applicationContext, "REL", Toast.LENGTH_SHORT).show()
                    genreFile.text = "Literature"
                }
                else if (list[which] == "Science"){
                    Toast.makeText(context!!.applicationContext, "REL", Toast.LENGTH_SHORT).show()
                    genreFile.text = "Science"
                }
                else if (list[which] == "Motivation"){
                    Toast.makeText(context!!.applicationContext, "MTV", Toast.LENGTH_SHORT).show()
                    genreFile.text = "Motivation"
                }

                dialog.dismiss()
            }
            builder.setNeutralButton("Cancel"){ dialog, which ->
                dialog.dismiss()
            }

            val alertDialog = builder.create()

            alertDialog.setOnShowListener(object : DialogInterface.OnShowListener {
                @SuppressLint("ResourceAsColor")
                override fun onShow(dialog: DialogInterface?) {
                    alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(R.color.mainColor)
                }
            })

            alertDialog.show()
        }

        addImage.setOnClickListener{
            val intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select Image"), IMG_REQUEST_CODE)
        }

        addFile.setOnClickListener {
            val intent = Intent()
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.setType("*/*")
            val mimeTypes = arrayOf("application/pdf" , "audio/*")
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(Intent.createChooser(intent, "Select Book"), FILE_REQUEST_CODE)
        }

        addBook.setOnClickListener{
            viewModel.uploadData(formatFile, genreFile, fileTitle, fileAuthor, fileDescription)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.initActivityResult(requestCode, resultCode, data)

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

        fileTitle = view!!.findViewById(R.id.manage_titelET)
        fileAuthor = view!!.findViewById(R.id.manage_authorET)
        fileDescription = view!!.findViewById(R.id.manage_descriptionET)

        formatFile = view!!.findViewById(R.id.formatFileTV)
        genreFile = view!!.findViewById(R.id.genreFileTV)


    }

}