package com.e.booker.view.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import com.e.booker.R
import com.e.booker.view.ui.activities.auth.LoginAcitivity
import com.e.booker.viewmodel.fragmentviewmodel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    @SuppressLint("ResourceAsColor")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel





    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val getInf: MenuInflater = activity!!.menuInflater
        getInf.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }




}