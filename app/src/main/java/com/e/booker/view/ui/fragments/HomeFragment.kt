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

    private lateinit var logOut: Button
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

        logOut = view!!.findViewById(R.id.logOutBTN)

        logOut.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(context, LoginAcitivity::class.java)
            startActivity(intent)
            activity?.finish()
        }




    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val getInf: MenuInflater = activity!!.menuInflater

        getInf.inflate(R.menu.toolbar_menu, menu)

//        val profileMenuItem: MenuItem? = menu.findItem(R.id.profileITEM)
//        val view: View = MenuItemCompat.getActionView(profileMenuItem)
//
//        val profileImage: CircleImageView = view.findViewById(R.id.profileIMG)
//
//        Glide.with(context!!)
//                .load(R.drawable.user)
//                .into(profileImage)

//        profileImage.setOnClickListener{
//            val profSettings = ProfileSettingsFragment()
//            val transaction = fragmentManager!!.beginTransaction()
//            transaction.replace(R.id.fragmentContainer, profSettings)
//            transaction.addToBackStack(null)
//            transaction.commit()
//        }


        super.onCreateOptionsMenu(menu, inflater)
    }




}