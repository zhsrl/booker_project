package com.e.booker.view.ui.fragments.multichoiseFragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.e.booker.R
import java.lang.ClassCastException
import java.lang.Exception

class GenreMultiChoiseDialogFragment: DialogFragment() {

    interface OnMultiChoiseListener{
        fun positiveButtonPressed(list: Array<String>, selectedItem: ArrayList<String>)
        fun negativeButtonPressed()
    }

    lateinit var mListener: OnMultiChoiseListener

    private var selectedList: ArrayList<String> = ArrayList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mListener = context as OnMultiChoiseListener
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var alertBuilder: androidx.appcompat.app.AlertDialog.Builder = androidx.appcompat.app.AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
        var list: Array<String>
        list = context!!.resources.getStringArray(R.array.genre_items)

        alertBuilder.setTitle("Choise book format")
                .setMultiChoiceItems(list, null) {
                    dialog, which, isChecked ->

                    if(isChecked){
                        selectedList.add(list[which])
                    } else{
                        selectedList.remove(list[which])
                    }

                }.setPositiveButton("Ok", object: DialogInterface.OnClickListener{
                    @SuppressLint("WrongConstant")
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        mListener.positiveButtonPressed(list, selectedList)
                        setStyle(R.style.AlertDialogTheme, theme)
                    }

                }).setNegativeButton("Cancel", object: DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        mListener.negativeButtonPressed()
                    }

                })

        return alertBuilder.create()
    }




}