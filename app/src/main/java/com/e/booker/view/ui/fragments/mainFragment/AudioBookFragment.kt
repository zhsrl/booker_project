package com.e.booker.view.ui.fragments.mainFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.e.booker.R
import com.e.booker.viewmodel.fragmentviewmodel.AudioBookViewModel

class AudioBookFragment : Fragment() {

    companion object {
        fun newInstance() = AudioBookFragment()
    }

    private lateinit var viewModel: AudioBookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.audio_book_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AudioBookViewModel::class.java)
        // TODO: Use the ViewModel
    }

}