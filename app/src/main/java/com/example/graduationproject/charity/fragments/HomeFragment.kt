package com.example.graduationproject.charity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.graduationproject.R
import com.example.graduationproject.donor.adapters.DonationTypeAdapter
import com.example.graduationproject.donor.adapters.SectionsPagerAdapter
import com.example.graduationproject.donor.models.Campaigns
import com.example.graduationproject.donor.models.DonationType
import kotlinx.android.synthetic.main.activity_charity_complete_signup.*
import kotlinx.android.synthetic.main.activity_charity_complete_signup.rv_complete_signup_donation_type
import kotlinx.android.synthetic.main.fragment_charity_home.*
import kotlinx.android.synthetic.main.fragment_charity_home.view.*
import kotlinx.android.synthetic.main.tab_content.*
import kotlinx.android.synthetic.main.tab_content.view.*


class HomeFragment : Fragment() ,View.OnClickListener{
     var allDonationChecked = false
     var moneyDonationChecked = false
     var foodDonationChecked = false
     var clothesDonationChecked = false

    lateinit var fragments : ArrayList<Fragment>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_charity_home, container, false)

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
        root.charities_viewpager.adapter = sectionsPagerAdapter
//        root.charities_viewpager.rotationY = 180F

        root.donation_all.setCardBackgroundColor(resources.getColor(R.color.app_color))
        DrawableCompat.setTint(
            DrawableCompat.wrap(root.all_img.drawable),
            ContextCompat.getColor(this.requireContext(), R.color.white)
        )
        root.charities_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
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

                charities_viewpager.currentItem = 0
            }
            R.id.donation_money -> {
                allDonationChecked = false
                moneyDonationChecked = true
                foodDonationChecked = false
                clothesDonationChecked = false
                charities_viewpager.currentItem = 1
            }
            R.id.donation_food -> {
                allDonationChecked = false
                moneyDonationChecked = false
                foodDonationChecked = true
                clothesDonationChecked = false
                charities_viewpager.currentItem = 2
            }
            R.id.donation_clothes -> {
                allDonationChecked = false
                moneyDonationChecked = false
                foodDonationChecked = false
                clothesDonationChecked = true
                charities_viewpager.currentItem = 3

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