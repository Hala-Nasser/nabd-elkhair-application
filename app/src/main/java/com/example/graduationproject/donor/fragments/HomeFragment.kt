package com.example.graduationproject.donor.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.graduationproject.adapters.SectionsPagerAdapter
import com.example.graduationproject.api.category.CategoryJson
import com.example.graduationproject.api.category.Data
import com.example.graduationproject.charity.fragments.AllDonationFragment
import com.example.graduationproject.charity.fragments.ClothesDonationFragment
import com.example.graduationproject.charity.fragments.FoodDonationFragment
import com.example.graduationproject.charity.fragments.MoneyDonationFragment
import com.example.graduationproject.donor.adapters.CampaignsAdapter
import com.example.graduationproject.donor.adapters.CharitiesAdapter
import com.example.graduationproject.donor.adapters.DonationTypeAdapter
import com.example.graduationproject.donor.models.Campaigns
import com.example.graduationproject.donor.models.Charity
import com.example.graduationproject.donor.models.DonationType
import com.example.graduationproject.network.ApiRequests
import com.example.graduationproject.network.RetrofitInstance
import com.example.mystory2.api.story.StoryJson
import kotlinx.android.synthetic.main.activity_donor_main.*
import kotlinx.android.synthetic.main.fragment_all_donation.view.*
import kotlinx.android.synthetic.main.fragment_charity_home.*
import kotlinx.android.synthetic.main.fragment_charity_home.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.tab_content.*
import kotlinx.android.synthetic.main.tab_content.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

class HomeFragment : Fragment(),View.OnClickListener {

    lateinit var rv_donation_type: RecyclerView
    var categoriesList = listOf<DonationType>()

    private lateinit var  charitiesList: MutableList<Charity>

    var allDonationChecked = false
    var moneyDonationChecked = false
    var foodDonationChecked = false
    var clothesDonationChecked = false

    lateinit var fragments : ArrayList<Fragment>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

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
        root.charities_viewpager.adapter = sectionsPagerAdapter
//        root.charities_viewpager.rotationY = 180F


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

        charitiesList = mutableListOf()
        charitiesList.add(Charity(R.drawable.charity_image,"جمعية الاحسان الخيرية","فلسطين, غزة"))
        charitiesList.add(Charity(R.drawable.charity_image,"جمعية الاحسان الخيرية","فلسطين, غزة"))
        charitiesList.add(Charity(R.drawable.charity_image,"جمعية الاحسان الخيرية","فلسطين, غزة"))
        charitiesList.add(Charity(R.drawable.charity_image,"جمعية الاحسان الخيرية","فلسطين, غزة"))




        root.rv_all_charities.layoutManager = LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
        root.rv_all_charities.setHasFixedSize(true)
        val charitiesAdapter =
            CharitiesAdapter(this.activity, charitiesList)
        root.rv_all_charities.adapter = charitiesAdapter
        root.rv_all_charities


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


//    fun getCategories(){
//        Log.e("TAG", "stories")
//
//        val response = RetrofitInstance.create().getCategories()
//
//        response.enqueue(object : Callback<CategoryJson> {
//            override fun onResponse(call: Call<CategoryJson>, response: Response<CategoryJson>) {
//                if (response.isSuccessful) {
//                    val data = response.body()
//                    Log.e("TAG", "Stories success")
////                    categoriesList = data!!.data
//                    rv_donation_type.layoutManager = LinearLayoutManager(activity,RecyclerView.HORIZONTAL,false)
//                    rv_donation_type.setHasFixedSize(true)
//                    val storyAdapter =
//                        DonationTypeAdapter(activity, categoriesList,"DonorHome")
//                    rv_donation_type.adapter = storyAdapter
//                } else {
//                    Log.e("TAG", "Stories unsuccess")
//                }
//
//            }
//
//            override fun onFailure(call: Call<CategoryJson>, t: Throwable) {
//                Log.e("TAG", t.message!!)
//            }
//        })
//    }

}