package com.example.user.nevigationdrawer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import android.view.MenuItem
import android.support.v4.view.GravityCompat
import android.support.v7.widget.Toolbar


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mDrawerLayout: DrawerLayout
    private val handlerSaveId = Handler()
    private val DRAWER_CLOSE_DELAY: Long = 350
    private var toggle: ActionBarDrawerToggle? = null
    private var activeMenu: Int = 0
    private var toolbar: Toolbar? = null
    private val ID_MENU_ACTIVE = "IdMenuActive"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDrawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val mNavigationView: NavigationView = findViewById(R.id.nevigation_drawer)
        if (savedInstanceState == null) {
            activeMenu = R.id.image
        } else {
            activeMenu = savedInstanceState.getInt(ID_MENU_ACTIVE)
        }
        mNavigationView.setNavigationItemSelectedListener(this)
        mNavigationView.menu.findItem(activeMenu).isChecked = true
        toggle = ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.open, R.string.close)
        mDrawerLayout.addDrawerListener(toggle!!)
        toggle!!.syncState()
        switchFragment(activeMenu)


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        item.setChecked(true)
        activeMenu = item.getItemId()
        mDrawerLayout.closeDrawer(GravityCompat.START)
        handlerSaveId.postDelayed({ switchFragment(item.getItemId()) }, DRAWER_CLOSE_DELAY)
        return true
    }

    private fun switchFragment(item: Any) {
        when (item) {
            R.id.image -> {
                val fragmentImage: FragmentImage = FragmentImage()
                generalFragment(fragmentImage);
            }
            R.id.video -> {
                val fragmentVideo: FragmentVideo = FragmentVideo()
                generalFragment(fragmentVideo);
            }
            R.id.audio -> {
                val fragmentAudio: FragmentAudio = FragmentAudio()
                generalFragment(fragmentAudio)

            }
        }
        mDrawerLayout.closeDrawer(Gravity.START)

    }

    fun generalFragment(fragment: Fragment) {
        val fragmentTransition: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransition.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        fragmentTransition.replace(R.id.frame_layout, fragment)
        fragmentTransition.commitAllowingStateLoss()
        /* Fragment fragment = loadFragment();
         FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
         fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                 android.R.anim.fade_out);
         fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
         fragmentTransaction.commitAllowingStateLoss();*/
    }

    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}
