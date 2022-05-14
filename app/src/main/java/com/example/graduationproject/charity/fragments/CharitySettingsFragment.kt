package com.example.graduationproject.charity.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.graduationproject.R
import com.example.graduationproject.adapters.SectionsPagerAdapter
import com.example.graduationproject.classes.TabLayoutSettings
import com.example.graduationproject.classes.ZoomOutPageTransformer
import com.example.graduationproject.network.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_charity_main.*
import kotlinx.android.synthetic.main.fragment_charity_profile.*
import kotlinx.android.synthetic.main.fragment_charity_profile.charity_profile_image
import kotlinx.android.synthetic.main.fragment_charity_settings.*
import kotlinx.android.synthetic.main.fragment_charity_settings.view.*

class CharitySettingsFragment : Fragment() {

    private val TAB_ICONS = arrayOf(
        R.drawable.ic_settings,
        R.drawable.ic_payment,
        R.drawable.ic_notifications
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_charity_settings, container, false)

        requireActivity().charity_nav_bottom.visibility=View.GONE
        var b = arguments
        val sharedPref = requireActivity().getSharedPreferences(
            "sharedPref", Context.MODE_PRIVATE)

            Picasso.get().load(RetrofitInstance.IMAGE_URL+sharedPref.getString("image","")).into(root.setting_profile_image)
            root.settings_user_name.text = sharedPref.getString("name","")
            root.settings_user_email.text = sharedPref.getString("email","")

        val sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)
        sectionsPagerAdapter.addFragmentsAndTitles(GeneralSettingsFragment(),"الاعدادات")
        sectionsPagerAdapter.addFragmentsAndTitles(PaymentSettingsFragment(),"الدفع")
        sectionsPagerAdapter.addFragmentsAndTitles(NotificationsSettingsFragment(),"الاشعارات")


        root.charity_settings_view_pager.adapter = sectionsPagerAdapter

        root.charity_settings_tab_layout.setupWithViewPager(root.charity_settings_view_pager)
        root.charity_settings_view_pager.setPageTransformer(true, ZoomOutPageTransformer())

        if (b!=null){
            if (b.getString("from")=="PaymentSetting"){
                root.charity_settings_view_pager.currentItem = 1
            }
        }

        var tabLayout = TabLayoutSettings()
        tabLayout.setupTabIcons(root.charity_settings_tab_layout,TAB_ICONS)


        root.back.setOnClickListener {
            requireActivity().onBackPressed()
        }


        return root
    }

}