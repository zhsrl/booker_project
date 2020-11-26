package com.e.booker.view.ui.activities.home

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.e.booker.R
import com.e.booker.utils.Pager
import com.e.booker.view.ui.fragments.*
import com.e.booker.viewmodel.ViewModelProviderFactory
import com.e.booker.viewmodel.activityviewmodel.HomeViewModel
import com.e.progress.ViewPageAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.hdodenhof.circleimageview.CircleImageView

class HomeActivity : AppCompatActivity() {
    private lateinit var bottomNavBar: BottomNavigationView

    //Custom Toolbar elements
    private lateinit var profileImage: CircleImageView
    private lateinit var toolbarTitle: TextView
    private lateinit var toolbarSearch: ImageView

    private lateinit var pager: Pager
    private lateinit var pagerAdapter: ViewPageAdapter

    var homeFragment = HomeFragment()
    var readingFragment = ReadingFragment()
    var audioBookFragment = AudioBookFragment()
    var bookFragment = BookFragment()
    var fragmentList: MutableList<Fragment> = ArrayList()

    private lateinit var homeViewModel: HomeViewModel

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val providerFactory = ViewModelProviderFactory(this)
        homeViewModel = ViewModelProvider(this, providerFactory).get(HomeViewModel::class.java)

        init()

        homeViewModel.showImage(profileImage)

        bottomNavBar = findViewById(R.id.bottom_nav_bar)

        fragmentList.add(homeFragment)
        fragmentList.add(readingFragment)
        fragmentList.add(audioBookFragment)
        fragmentList.add(bookFragment)
        pager = findViewById(R.id.pager)
        pagerAdapter = ViewPageAdapter(supportFragmentManager, fragmentList)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, HomeFragment())
            .commit()
        toolbarTitle.text = "Басты бет"


        Glide.with(applicationContext)
                .load(R.drawable.user)
                .into(profileImage)

        profileImage.setOnClickListener{
            val intent = Intent(applicationContext, ProfileSettingsActivity::class.java)
            startActivity(intent)
        }

        toolbarSearch.setOnClickListener{
            Toast.makeText(applicationContext,"Coming soon can you search anything!",Toast.LENGTH_SHORT).show()
        }


        bottomNavBar.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                var selectedFragment: Fragment? = null
                when(item.itemId){
                    R.id.home_item -> {
                        selectedFragment = HomeFragment()
                        val transaction = supportFragmentManager.beginTransaction()
                        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        transaction.replace(R.id.fragmentContainer, selectedFragment)
                        transaction.addToBackStack  (null)
                        transaction.commit()
                        toolbarTitle.text = "Басты бет"
                        item.setChecked(true)
                    }
                    R.id.lastestRead_item -> {
                        selectedFragment = ReadingFragment()
                        val transaction = supportFragmentManager.beginTransaction()
                        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        transaction.replace(R.id.fragmentContainer, selectedFragment)
                        transaction.addToBackStack  (null)
                        transaction.commit()
                        toolbarTitle.text = "Соңғы оқылым"
                        item.setChecked(true)
                    }
                    R.id.audioBook_item -> {
                        selectedFragment = AudioBookFragment()
                        val transaction = supportFragmentManager.beginTransaction()
                        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        transaction.replace(R.id.fragmentContainer, selectedFragment)
                        transaction.addToBackStack  (null)
                        transaction.commit()
                        toolbarTitle.text = "Аудио кітап"
                        item.setChecked(true)
                    }
                    R.id.book_item ->{
                        selectedFragment = BookFragment()
                        val transaction = supportFragmentManager.beginTransaction()
                        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        transaction.replace(R.id.fragmentContainer, selectedFragment)
                        transaction.addToBackStack  (null)
                        transaction.commit()
                        toolbarTitle.text = "Кітап"
                        item.setChecked(true)
                    }

                }

                return false
            }
        })



    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    fun init(){
        profileImage = findViewById(R.id.toolbarUserImageCIV)
        toolbarTitle = findViewById(R.id.toolbarTitleTV)
        toolbarSearch = findViewById(R.id.searchImageIV)
    }


}