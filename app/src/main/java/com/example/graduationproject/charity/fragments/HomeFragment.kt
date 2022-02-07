package com.example.graduationproject.charity.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.viewpager.widget.ViewPager
import com.example.graduationproject.R
import com.example.graduationproject.adapters.SectionsPagerAdapter
import com.ramotion.circlemenu.CircleMenuView
import kotlinx.android.synthetic.main.activity_charity_complete_signup.*
import kotlinx.android.synthetic.main.activity_charity_main.*
import kotlinx.android.synthetic.main.activity_donor_main.*
import kotlinx.android.synthetic.main.fragment_charity_home.*
import kotlinx.android.synthetic.main.tab_content.*
import kotlinx.android.synthetic.main.tab_content.view.*
import android.view.View.OnTouchListener
import androidx.activity.OnBackPressedCallback
import kotlinx.android.synthetic.main.campaign_added_dialog.view.*
import kotlinx.android.synthetic.main.fragment_charity_home.view.*
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.widget.LinearLayout
import com.example.graduationproject.classes.TabLayoutSettings
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_charity_main.view.*
import kotlinx.android.synthetic.main.fragment_charity_profile.view.*
import kotlinx.android.synthetic.main.fragment_donation.view.*
import net.cachapa.expandablelayout.ExpandableLayout




class HomeFragment : Fragment(){

        private val TAB_ICONS = arrayOf(
            R.drawable.all,
            R.drawable.money,
            R.drawable.food,
            R.drawable.clothes,
        )
     var addCampaign = false
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_charity_home, container, false)

        requireActivity().charity_nav_bottom.visibility=View.VISIBLE
        if (arguments!=null) {
             addCampaign = requireArguments().getBoolean("addCampaign")
            if (addCampaign) {
                getDialog()
            }else{
                requireActivity().charity_nav_bottom.alpha = 1f
                root.home_expandable_layout.isExpanded = false
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)
        sectionsPagerAdapter.addFragments(AllDonationFragment())
        sectionsPagerAdapter.addFragments(MoneyDonationFragment())
        sectionsPagerAdapter.addFragments(FoodDonationFragment())
        sectionsPagerAdapter.addFragments(ClothesDonationFragment())
        root.campaign_viewpager.adapter = sectionsPagerAdapter
        root.charity_home_tab_layout.setupWithViewPager(root.campaign_viewpager)

        var tabLayout = TabLayoutSettings()
        tabLayout.setupTabIcons(root.charity_home_tab_layout,TAB_ICONS)
        tabLayout.setTabMargin(root.charity_home_tab_layout,10,10,100)

        root.campaign_viewpager.setOnTouchListener(OnTouchListener { v, event ->
            when (root.campaign_viewpager.currentItem) {
                0 -> {
                    root.campaign_viewpager.setCurrentItem(-1, false)
                    return@OnTouchListener true
                }
                1 -> {
                    root.campaign_viewpager.setCurrentItem(1-1, false)
                    root.campaign_viewpager.setCurrentItem(1, false)
                    return@OnTouchListener true
                }
                2 -> {
                    root.campaign_viewpager.setCurrentItem(2-1, false)
                    root.campaign_viewpager.setCurrentItem(2, false)
                    return@OnTouchListener true
                }
                3 -> {
                    root.campaign_viewpager.setCurrentItem(3-1, false)
                    root.campaign_viewpager.setCurrentItem(3, false)
                    return@OnTouchListener true
                }
                else -> true
            }
        })

        getExpandedfab(root)



        return root
    }

    private fun getExpandedfab(root: View) {

        root.edit_campaign_btn.setOnClickListener {
            if (root.home_expandable_layout.state == ExpandableLayout.State.COLLAPSED) {
                changeFabStyle(root,R.drawable.ic_close,R.color.white,0.5f,R.color.grey_white,true)
                expendFABMenu(root)
            } else if (root.home_expandable_layout.state == ExpandableLayout.State.EXPANDED) {

                changeFabStyle(root,R.drawable.ic_add,R.color.app_color,1f,R.color.white,false)
                collapseFABMenu(root)
            }
        }

        root.food_fab.setOnClickListener {
            var bundle = Bundle()
            bundle.putString("donationType", "food")
            var fragment = AddCampaignFragment()
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.charityContainer, fragment).addToBackStack(null).commit()
        }

        root.clothes_fab.setOnClickListener {
            var bundle = Bundle()
            bundle.putString("donationType", "clothes")
            var fragment = AddCampaignFragment()
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.charityContainer, fragment).addToBackStack(null).commit()
        }
    }

    private fun changeFabStyle(root: View,icon:Int,color:Int,alpha:Float,tint:Int,isExpanded:Boolean) {
        root.edit_campaign_btn.setImageResource(icon)
        root.edit_campaign_btn.backgroundTintList =
            ColorStateList.valueOf(resources.getColor(color))
        root.linearLayout2.alpha = alpha
        DrawableCompat.setTint(
            DrawableCompat.wrap(root.edit_campaign_btn.drawable),
            ContextCompat.getColor(requireContext(), tint)
        )
        requireActivity().charity_nav_bottom.alpha = alpha
        root.home_expandable_layout.isExpanded = isExpanded
    }

    private fun expendFABMenu(root: View) {
        root.edit_campaign_btn.animate().rotationBy(90F)
        root.food_fab.animate().translationY(0f)
        root.clothes_fab.animate().translationY(0f)
    }

    private fun collapseFABMenu(root: View) {
        root.edit_campaign_btn.animate().rotationBy(90F)
        root.food_fab.animate().translationY(-55f)
        root.clothes_fab.animate().translationY(-105f)
    }

    fun getDialog(){
        var view= layoutInflater.inflate(R.layout.campaign_added_dialog,null)
        val campaignDialog = Dialog(this.requireContext())
        campaignDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        campaignDialog.setContentView(view)
        view.close_dialog.setOnClickListener {
            campaignDialog.dismiss()
        }
        campaignDialog.setCancelable(false)
        campaignDialog.show()
    }


}