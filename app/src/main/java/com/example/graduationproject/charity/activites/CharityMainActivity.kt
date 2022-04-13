package com.example.graduationproject.charity.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.R
import com.example.graduationproject.charity.fragments.DonationFragment
import com.example.graduationproject.charity.fragments.HomeFragment
import com.example.graduationproject.charity.fragments.NotificationFragment
import com.example.graduationproject.charity.fragments.ProfileFragment
import com.example.graduationproject.classes.LuncherManager
import com.example.graduationproject.classes.NavigationManager
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class CharityMainActivity : AppCompatActivity() {
    lateinit var nav_bottom : ChipNavigationBar
    lateinit var navigationManager : NavigationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charity_main)
        GeneralChanges().setStatusBarTransparent(this)
        GeneralChanges().fadeTransition(this)

        nav_bottom = findViewById(R.id.charity_nav_bottom)
        navigationManager = NavigationManager(this)

        when(navigationManager.getPageName()){
            R.id.home -> {
                nav_bottom.setItemSelected(R.id.home)
            }
            R.id.donation -> {
                nav_bottom.setItemSelected(R.id.donation)
            }
            R.id.notification -> {
                nav_bottom.setItemSelected(R.id.notification)
            }
            R.id.profile -> {
                nav_bottom.setItemSelected(R.id.profile)
            }
        }

        replaceFragment(HomeFragment())
        nav_bottom.setOnItemSelectedListener {
            when (it) {

                R.id.home -> {
                    replaceFragment(HomeFragment())
                }
                R.id.donation -> {
                    replaceFragment(DonationFragment())
                }
                R.id.notification -> {
                    replaceFragment(NotificationFragment())

                }
                R.id.profile -> {
                    replaceFragment(ProfileFragment())
                }
            }
        }
    }

    fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(
            R.id.charityContainer,
            fragment).commit()

    }

}