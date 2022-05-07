package com.example.graduationproject.donor.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.adapters.SectionsPagerAdapter
import com.example.graduationproject.api.donorApi.donationType.DonationTypeJson
import com.example.graduationproject.classes.TabLayoutSettings
import com.example.graduationproject.donor.adapters.CharitiesAdapter
import com.example.graduationproject.donor.adapters.PageAdapterDonationType
import com.example.graduationproject.donor.models.Charity
import com.example.graduationproject.network.RetrofitInstance
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_donor_main.*
import kotlinx.android.synthetic.main.fragment_all_donation.view.*
import kotlinx.android.synthetic.main.fragment_charity_home.*
import kotlinx.android.synthetic.main.fragment_charity_home.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.tab_content.*
import kotlinx.android.synthetic.main.tab_content.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.os.Build





class HomeFragment : Fragment(), CharitiesAdapter.onCharityItemClickListener {

    private lateinit var  charitiesList: MutableList<Charity>
//    private var donation_type_ids = arrayOf<Int>()
    var donation_type_ids = ArrayList<Int>()
    lateinit var sharedPref : SharedPreferences
    lateinit var editor : SharedPreferences.Editor

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        requireActivity().nav_bottom.visibility=View.VISIBLE

        sharedPref= requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        editor = sharedPref.edit()
        val user_image = sharedPref.getString("user_image", "")
        Log.e("image", user_image!!)

        Picasso.get().load(RetrofitInstance.IMAGE_URL+user_image).into(root.profile_image)

        getDonationType()

//        val sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)
//        sectionsPagerAdapter.addFragments(CampaignsAccordingToDonationTypeFragment())
//        root.campaigns_viewpager.adapter = sectionsPagerAdapter
//        root.donor_home_tab_layout.setupWithViewPager(root.campaigns_viewpager)

        root.search.setOnSearchClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, SearchResultsFragment()).addToBackStack(null).commit()
            requireActivity().nav_bottom.visibility=View.GONE
        }

        root.donor_home_tab_layout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.e("on select tab", "enter")
                var mSelectedPosition = root.donor_home_tab_layout.selectedTabPosition
                editor.putInt("selected home donation type", donation_type_ids[mSelectedPosition])
                editor.apply()
                val donation_type = sharedPref.getInt("selected home donation type", 0)

                root.campaigns_viewpager.currentItem = tab.position

                Log.e("donation type", donation_type.toString())
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


//        root.donor_home_tab_layout.addOnTabSelectedListener(object : OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                var mSelectedPosition = root.donor_home_tab_layout.selectedTabPosition
//                editor.putInt("selected home donation type", donation_type_ids[mSelectedPosition])
//                editor.apply()
//                adapter.notifyDataSetChanged()
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab) {}
//            override fun onTabReselected(tab: TabLayout.Tab) {}
//        })

//        root.donor_home_tab_layout.addOnTabSelectedListener(object : OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                val position = tab.position
//                val sharedPref = activity!!.getSharedPreferences(
//                    "sharedPref", Context.MODE_PRIVATE)
//
//                val editor = sharedPref.edit()
//                editor.putInt("selected home donation type", donation_type_ids[position])
//                editor.apply()
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                TODO()
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//                root.donor_home_tab_layout.selectTab(tab)
//            }
//        })


//        root.campaigns_viewpager.setOnTouchListener(OnTouchListener { v, event ->
//
//            val position = root.campaigns_viewpager.currentItem
//
//            editor.putInt("selected home donation type", donation_type_ids[position])
//            editor.apply()
//
//            root.campaigns_viewpager.setCurrentItem(position-1, false)
//            return@OnTouchListener true
//
////            when (root.campaigns_viewpager.currentItem) {
////                0 -> {
////                    root.campaigns_viewpager.setCurrentItem(-1, false)
////                    return@OnTouchListener true
////                }
////                1 -> {
////                    root.campaigns_viewpager.setCurrentItem(1-1, false)
////                    root.campaigns_viewpager.setCurrentItem(1, false)
////                    return@OnTouchListener true
////                }
////                2 -> {
////                    root.campaigns_viewpager.setCurrentItem(2-1, false)
////                    root.campaigns_viewpager.setCurrentItem(2, false)
////                    return@OnTouchListener true
////                }
////                3 -> {
////                    root.campaigns_viewpager.setCurrentItem(3-1, false)
////                    root.campaigns_viewpager.setCurrentItem(3, false)
////                    return@OnTouchListener true
////                }
////                else -> true
////            }
//        })



        charitiesList = mutableListOf()
        charitiesList.add(Charity(R.drawable.charity_image,"جمعية الاحسان الخيرية","فلسطين, غزة"))
        charitiesList.add(Charity(R.drawable.charity_image,"جمعية الاحسان الخيرية","فلسطين, غزة"))
        charitiesList.add(Charity(R.drawable.charity_image,"جمعية الاحسان الخيرية","فلسطين, غزة"))
        charitiesList.add(Charity(R.drawable.charity_image,"جمعية الاحسان الخيرية","فلسطين, غزة"))


        root.rv_all_charities.layoutManager = LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
        root.rv_all_charities.setHasFixedSize(true)
        val charitiesAdapter =
            CharitiesAdapter(this.activity, charitiesList, this)
        root.rv_all_charities.adapter = charitiesAdapter

        return root
    }



    override fun onItemClick(data: Charity, position: Int) {
        val fragment = CharityDetailsFragment()
        val b = Bundle()
        b.putString("campaign_name", data.charityName)
        b.putInt("campaign_image", data.charityImg!!)
        b.putString("campaign_date", data.charityLocation)

        fragment.arguments = b

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, fragment).addToBackStack(null).commit()
        requireActivity().nav_bottom.visibility=View.GONE
    }

    fun getDonationType() {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getDonationType()

        response.enqueue(object : Callback<DonationTypeJson> {
            override fun onResponse(call: Call<DonationTypeJson>, response: Response<DonationTypeJson>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data


                    for (donation_type in data){
                        donation_type_ids.add(donation_type.id)
                        donor_home_tab_layout.addTab(donor_home_tab_layout.newTab().setText(donation_type.name))
                    }
                    TabLayoutSettings().setTabMargin(donor_home_tab_layout, 10, 10, 100)
                    Log.e("donation_type_ids", donation_type_ids.size.toString())
                    editor.putInt("selected home donation type", donation_type_ids[0])
                    editor.apply()

                    // Create adapter after adding the tabs
                    val adapter = PageAdapterDonationType(childFragmentManager, donation_type_ids.size)
                    campaigns_viewpager.adapter = adapter
                    campaigns_viewpager.addOnPageChangeListener(TabLayoutOnPageChangeListener(donor_home_tab_layout))

                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<DonationTypeJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
    }

}