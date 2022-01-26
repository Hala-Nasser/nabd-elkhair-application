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
import kotlinx.android.synthetic.main.campaign_added_dialog.view.*
import kotlinx.android.synthetic.main.fragment_charity_home.view.*


class HomeFragment : Fragment() ,View.OnClickListener{
     var allDonationChecked = false
     var moneyDonationChecked = false
     var foodDonationChecked = false
     var clothesDonationChecked = false

    lateinit var fragments : ArrayList<Fragment>

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_charity_home, container, false)

        requireActivity().charity_nav_bottom.visibility=View.VISIBLE
        if (arguments!=null) {
            var addCampaign = requireArguments().getBoolean("addCampaign")
            if (addCampaign) {
                getDialog()
            }
        }
        root.donation_all.setOnClickListener(this)
        root.donation_money.setOnClickListener(this)
        root.donation_food.setOnClickListener(this)
        root.donation_clothes.setOnClickListener(this)

        fragments = ArrayList<Fragment>()

        fragments.add(AllDonationFragment())
        fragments.add(MoneyDonationFragment())
        fragments.add(FoodDonationFragment())
        fragments.add(ClothesDonationFragment())

        val sectionsPagerAdapter = SectionsPagerAdapter(requireContext(), childFragmentManager ,fragments)
        root.campaign_viewpager.adapter = sectionsPagerAdapter
//        root.campaign_viewpager.rotationY = 180F

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
        root.circleMenu.eventListener = object : CircleMenuView.EventListener() {
            override fun onMenuOpenAnimationStart(view: CircleMenuView) {
                super.onMenuOpenAnimationStart(view)
            }

            override fun onButtonClickAnimationStart(view: CircleMenuView, buttonIndex: Int) {
                super.onButtonClickAnimationStart(view, buttonIndex)
                when(buttonIndex){
                    0 -> {
                         var bundle = Bundle()
                        bundle.putString("donationType","food")
                        var fragment = AddCampaignFragment()
                        fragment.arguments = bundle
                        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.charityContainer,fragment).addToBackStack(null).commit()

//                        var i = Intent(this@HomeFragment.context, AddCampaignActivity::class.java)
//                        startActivity(i)
                    }
                    1 -> {
                        var bundle = Bundle()
                        bundle.putString("donationType","clothes")
                        var fragment = AddCampaignFragment()
                        fragment.arguments = bundle
                        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.charityContainer,fragment).addToBackStack(null).commit()
//                        var i = Intent(this@HomeFragment.context, AddCampaignActivity::class.java)
//                        startActivity(i)
                    }

                }
            }
        }

        //root.campaign_viewpager.swipeLocked =false
        root.campaign_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

                when (position){
                    0 -> {
                        allDonationChecked = true
                        moneyDonationChecked = false
                        foodDonationChecked = false
                        clothesDonationChecked = false
                        changeTabBarItem()
                    }
                    1 -> {
                        allDonationChecked = false
                        moneyDonationChecked = true
                        foodDonationChecked = false
                        clothesDonationChecked = false
                        changeTabBarItem()
                    }
                    2 -> {
                        allDonationChecked = false
                        moneyDonationChecked = false
                        foodDonationChecked = true
                        clothesDonationChecked = false
                        changeTabBarItem()
                    }
                    3 -> {
                        allDonationChecked = false
                        moneyDonationChecked = false
                        foodDonationChecked = false
                        clothesDonationChecked = true
                        changeTabBarItem()
                    }
                }

            }

        })

        return root
    }

    override fun onClick(p0: View?) {
        changeTabBarItem()
        when(p0!!.id){
            R.id.donation_all -> {
                allDonationChecked = true
                moneyDonationChecked = false
                foodDonationChecked = false
                clothesDonationChecked = false

                campaign_viewpager.currentItem = 0
            }
            R.id.donation_money -> {
                allDonationChecked = false
                moneyDonationChecked = true
                foodDonationChecked = false
                clothesDonationChecked = false
                campaign_viewpager.currentItem = 1
            }
            R.id.donation_food -> {
                allDonationChecked = false
                moneyDonationChecked = false
                foodDonationChecked = true
                clothesDonationChecked = false
                campaign_viewpager.currentItem = 2
            }
            R.id.donation_clothes -> {
                allDonationChecked = false
                moneyDonationChecked = false
                foodDonationChecked = false
                clothesDonationChecked = true
                campaign_viewpager.currentItem = 3

            }
        }
    }

    private fun changeTabBarItem() {
        if (allDonationChecked) {
            donation_all.setCardBackgroundColor(resources.getColor(R.color.app_color))
            DrawableCompat.setTint(
                DrawableCompat.wrap(all_img.drawable),
                ContextCompat.getColor(this.requireContext(), R.color.white)
            )
        }else {
            donation_all.setCardBackgroundColor(resources.getColor(R.color.white))
            DrawableCompat.setTint(
                DrawableCompat.wrap(all_img.drawable),
                ContextCompat.getColor(this.requireContext(), R.color.black)
            )
        }
        if (moneyDonationChecked) {
            donation_money.setCardBackgroundColor(resources.getColor(R.color.app_color))
            DrawableCompat.setTint(
                DrawableCompat.wrap(money_img.drawable),
                ContextCompat.getColor(this.requireContext(), R.color.white)
            )
        }else {
            donation_money.setCardBackgroundColor(resources.getColor(R.color.white))
            DrawableCompat.setTint(
                DrawableCompat.wrap(money_img.drawable),
                ContextCompat.getColor(this.requireContext(), R.color.black)
            )
        }

        if (foodDonationChecked) {
            donation_food.setCardBackgroundColor(resources.getColor(R.color.app_color))
            DrawableCompat.setTint(
                DrawableCompat.wrap(food_img.drawable),
                ContextCompat.getColor(this.requireContext(), R.color.white)
            )
        }else {
            donation_food.setCardBackgroundColor(resources.getColor(R.color.white))
            DrawableCompat.setTint(
                DrawableCompat.wrap(food_img.drawable),
                ContextCompat.getColor(this.requireContext(), R.color.black)
            )
        }


        if (clothesDonationChecked) {
            donation_clothes.setCardBackgroundColor(resources.getColor(R.color.app_color))
            DrawableCompat.setTint(
                DrawableCompat.wrap(clothes_img.drawable),
                ContextCompat.getColor(this.requireContext(), R.color.white)
            )
        }else {
            donation_clothes.setCardBackgroundColor(resources.getColor(R.color.white))
            DrawableCompat.setTint(
                DrawableCompat.wrap(clothes_img.drawable),
                ContextCompat.getColor(this.requireContext(), R.color.black)
            )
        }
    }


    fun getDialog(){
        var view= layoutInflater.inflate(R.layout.campaign_added_dialog,null)
        val campaignDialog = Dialog(this.requireContext())
        campaignDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        campaignDialog.setContentView(view)
        view.close_dialog.setOnClickListener {
            campaignDialog.dismiss()
        }
        campaignDialog.setCancelable(true)
        campaignDialog.show()
    }
}