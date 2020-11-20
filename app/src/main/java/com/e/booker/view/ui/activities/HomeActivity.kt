package com.e.booker.view.ui.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.e.booker.R
import com.e.booker.utils.Pager
import com.e.booker.view.ui.fragments.*
import com.e.progress.ViewPageAdapter
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import de.hdodenhof.circleimageview.CircleImageView

class HomeActivity : AppCompatActivity() {
    private lateinit var bottomNavBar: BottomNavigationView

    private lateinit var toolbar: MaterialToolbar
    private lateinit var profileImage: CircleImageView

    private lateinit var pager: Pager
    private lateinit var pagerAdapter: ViewPageAdapter
    var homeFragment = HomeFragment()
    var readingFragment = ReadingFragment()
    var audioBookFragment = AudioBookFragment()
    var bookFragment = BookFragment()

    var profileSettingsFragment = ProfileSettingsFragment()

    var fragmentList: MutableList<Fragment> = ArrayList()

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavBar = findViewById(R.id.bottom_nav_bar)

        fragmentList.add(homeFragment)

        //Profile Settings Fragment
        fragmentList.add(profileSettingsFragment)

        fragmentList.add(readingFragment)
        fragmentList.add(audioBookFragment)
        fragmentList.add(bookFragment)
        pager = findViewById(R.id.pager)
        pagerAdapter = ViewPageAdapter(supportFragmentManager, fragmentList)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, HomeFragment())
            .commit()

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setTitle("Басты бет")

        profileImage = findViewById(R.id.profileIMG)
        Glide.with(applicationContext)
                .load(R.drawable.user)
                .into(profileImage)

//        profileImage.setOnClickListener{
//            val profSettings = ProfileSettingsFragment()
//            val transaction = fragmentManager!!.beginTransaction()
//            transaction.replace(R.id.fragmentContainer, Fragment())
//            transaction.addToBackStack(null)
//            transaction.commit()
//        }

        bottomNavBar.setOnNavigationItemSelectedListener{ item ->
            var selectedFragment: Fragment? = null
            when(item.itemId){
                R.id.home_item -> {
                    selectedFragment = HomeFragment()
                    toolbar.title = "Басты бет"
                }
                R.id.lastestRead_item -> {
                    selectedFragment = ReadingFragment()
                    toolbar.title = "Соңғы оқылымдар"
                }
                R.id.audioBook_item -> {
                    selectedFragment = AudioBookFragment()
                    toolbar.title = "Аудио кітап"
                }
                R.id.book_item ->{
                    selectedFragment = BookFragment()
                    toolbar.title = "Кітап"
                }

            }

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, selectedFragment!!)
            transaction.addToBackStack(null)
            transaction.commit()
            return@setOnNavigationItemSelectedListener false

        }




    }

    override fun onStop() {
        super.onStop()
        finish()
    }


//    inner class SwipableViewPager(fm: FragmentManager): FragmentStatePagerAdapter(fm){
//        override fun getCount(): Int {
//            return 4
//        }
//
//        override fun getItem(position: Int): Fragment = HomeFragment()
//
//    }


}