package com.e.booker.view.ui.fragments.managerFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.e.booker.R
import com.e.booker.viewmodel.manageFragmentViewModel.ShowViewModel

class ShowFragment : Fragment() {

    companion object {
        fun newInstance() = ShowFragment()
    }

    private lateinit var viewModel: ShowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.show_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShowViewModel::class.java)
        // TODO: Use the ViewModel
    }

}