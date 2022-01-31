package com.example.graduationproject.donor.fragments

import android.annotation.SuppressLint
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
import com.example.graduationproject.charity.fragments.AllDonationFragment
import com.example.graduationproject.charity.fragments.ClothesDonationFragment
import com.example.graduationproject.charity.fragments.FoodDonationFragment
import com.example.graduationproject.charity.fragments.MoneyDonationFragment
import kotlinx.android.synthetic.main.activity_donor_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.tab_content.*
import kotlinx.android.synthetic.main.tab_content.view.*
import android.view.View.OnTouchListener

class ProfileFragment : Fragment(),View.OnClickListener {

    var allDonationChecked = true
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
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        requireActivity().nav_bottom.visibility=View.VISIBLE

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
        root.campaigns_viewpager.adapter = sectionsPagerAdapter

        root.campaigns_viewpager.setOnTouchListener(OnTouchListener { v, event ->
            when (root.campaigns_viewpager.currentItem) {
                0 -> {
                    root.campaigns_viewpager.setCurrentItem(-1, false)
                    return@OnTouchListener true
                }
                1 -> {
                    root.campaigns_viewpager.setCurrentItem(1-1, false)
                    root.campaigns_viewpager.setCurrentItem(1, false)
                    return@OnTouchListener true
                }
                2 -> {
                    root.campaigns_viewpager.setCurrentItem(2-1, false)
                    root.campaigns_viewpager.setCurrentItem(2, false)
                    return@OnTouchListener true
                }
                3 -> {
                    root.campaigns_viewpager.setCurrentItem(3-1, false)
                    root.campaigns_viewpager.setCurrentItem(3, false)
                    return@OnTouchListener true
                }
                else -> true
            }
        })

        root.campaigns_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
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

                campaigns_viewpager.currentItem = 0
            }
            R.id.donation_money -> {
                allDonationChecked = false
                moneyDonationChecked = true
                foodDonationChecked = false
                clothesDonationChecked = false
                campaigns_viewpager.currentItem = 1
            }
            R.id.donation_food -> {
                allDonationChecked = false
                moneyDonationChecked = false
                foodDonationChecked = true
                clothesDonationChecked = false
                campaigns_viewpager.currentItem = 2
            }
            R.id.donation_clothes -> {
                allDonationChecked = false
                moneyDonationChecked = false
                foodDonationChecked = false
                clothesDonationChecked = true
                campaigns_viewpager.currentItem = 3

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

}