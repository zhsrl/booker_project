package com.e.booker.utils

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.e.booker.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialog : BottomSheetDialogFragment(){

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.bottom_sheet_layout, null, false)

        return view


    }
}