package com.example.graduationproject.donor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.graduationproject.MyFirebaseMessagingService
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.R
import com.example.graduationproject.donor.fragments.HomeFragment
import com.example.graduationproject.donor.fragments.NotificationFragment
import com.example.graduationproject.donor.fragments.ProfileFragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class DonorMainActivity : AppCompatActivity() {
    val fragment = HomeFragment()
    lateinit var nav_bottom : ChipNavigationBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_main)
        GeneralChanges().setStatusBarTransparent(this)
        GeneralChanges().fadeTransition(this)

        MyFirebaseMessagingService().retrieveToken(this)

        nav_bottom = findViewById(R.id.nav_bottom)

        openMainFragment()

        nav_bottom.setItemSelected(R.id.home)
        nav_bottom.setOnItemSelectedListener {
            when (it) {

                R.id.home -> {
                    openMainFragment()
                }
                R.id.notification -> {
                    val favoriteFragment = NotificationFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainContainer, favoriteFragment).commit()

                }
                R.id.profile -> {
                    val profileFragment = ProfileFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainContainer, profileFragment).commit()
                    }
            }
        }
    }

    private fun openMainFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainContainer, fragment)
        transaction.commit()
    }
}