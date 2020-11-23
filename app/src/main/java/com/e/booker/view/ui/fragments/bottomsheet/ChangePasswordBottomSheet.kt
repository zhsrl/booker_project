package com.e.booker.view.ui.fragments.bottomsheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.e.booker.R
import com.e.booker.viewmodel.ViewModelProviderFactory
import com.e.booker.viewmodel.fragmentviewmodel.ChangePasswordViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton

class ChangePasswordBottomSheet: BottomSheetDialogFragment() {

    private lateinit var userCurrentPassword: EditText
    private lateinit var userNewPassword: EditText
    private lateinit var updatePassword: MaterialButton

    private lateinit var bottomSheetChangePasswordViewModel: ChangePasswordViewModel

    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.change_password_bottom_sheet, null, false)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetStyle)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val providerFactory = ViewModelProviderFactory(context!!)

        bottomSheetChangePasswordViewModel = ViewModelProvider(this, providerFactory).get(ChangePasswordViewModel::class.java)

        init()

        updatePassword.setOnClickListener {
            bottomSheetChangePasswordViewModel.changeData(userCurrentPassword, userNewPassword)
        }

    }

    fun init(){
        userCurrentPassword = view!!.findViewById(R.id.bsh_currentPasswordET)
        userNewPassword = view!!.findViewById(R.id.bsh_newPasswordET)
        updatePassword = view!!.findViewById(R.id.bsh_changePasswordBTN)
    }
}