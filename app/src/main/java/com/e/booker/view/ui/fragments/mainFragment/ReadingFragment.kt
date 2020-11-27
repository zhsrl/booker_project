package com.e.booker.view.ui.fragments.mainFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.e.booker.R
import com.e.booker.viewmodel.fragmentviewmodel.ReadingViewModel

class ReadingFragment : Fragment() {

    companion object {
        fun newInstance() = ReadingFragment()
    }

    private lateinit var viewModel: ReadingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.reading_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReadingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}