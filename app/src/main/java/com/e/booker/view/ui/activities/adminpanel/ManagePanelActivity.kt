package com.e.booker.view.ui.activities.adminpanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.e.booker.R
import com.e.booker.utils.Pager
import com.e.booker.view.ui.fragments.AudioBookFragment
import com.e.booker.view.ui.fragments.BookFragment
import com.e.booker.view.ui.fragments.HomeFragment
import com.e.booker.view.ui.fragments.ReadingFragment
import com.e.booker.view.ui.fragments.managerFragment.PanelFragment
import com.e.booker.view.ui.fragments.managerFragment.ShowFragment
import com.e.progress.ViewPageAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class ManagePanelActivity : AppCompatActivity() {

    private lateinit var bottomNavBar: BottomNavigationView

    private lateinit var pager: Pager
    private lateinit var pagerAdapter: ViewPageAdapter

    var addFragment = PanelFragment()
    var showFragment = ShowFragment()

    private var fragmentList: MutableList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manage_activity)

        init()

        fragmentList.add(addFragment)
        fragmentList.add(showFragment)

        //Pager Settings
        pager = findViewById(R.id.manage_pager)
        pagerAdapter = ViewPageAdapter(supportFragmentManager, fragmentList)

        supportFragmentManager.beginTransaction()
            .replace(R.id.managerFragmentContainer, addFragment)
            .commit()

        bottomNavBar.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                var selectedFragment: Fragment? = null
                when(item.itemId){
                    R.id.add_item -> {
                        selectedFragment = PanelFragment()
                        val transaction = supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.managerFragmentContainer, selectedFragment)
                        transaction.addToBackStack  (null)
                        transaction.commit()
                        item.setChecked(true)
                    }
                    R.id.show_item -> {
                        selectedFragment = ShowFragment()
                        val transaction = supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.managerFragmentContainer, selectedFragment)
                        transaction.addToBackStack  (null)
                        transaction.commit()
                        item.setChecked(true)
                    }

                }

                return false
            }
        })


    }

    private fun init(){
        bottomNavBar = findViewById(R.id.manage_bottom_nav_bar)
    }
}