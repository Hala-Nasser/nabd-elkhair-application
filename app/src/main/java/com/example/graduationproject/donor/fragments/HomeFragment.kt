package com.example.graduationproject.donor.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.adapters.SectionsPagerAdapter
import com.example.graduationproject.charity.fragments.AllDonationFragment
import com.example.graduationproject.charity.fragments.ClothesDonationFragment
import com.example.graduationproject.charity.fragments.FoodDonationFragment
import com.example.graduationproject.charity.fragments.MoneyDonationFragment
import com.example.graduationproject.donor.adapters.CharitiesAdapter
import com.example.graduationproject.donor.models.Charity
import kotlinx.android.synthetic.main.activity_donor_main.*
import kotlinx.android.synthetic.main.fragment_all_donation.view.*
import kotlinx.android.synthetic.main.fragment_charity_home.*
import kotlinx.android.synthetic.main.fragment_charity_home.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.tab_content.*
import kotlinx.android.synthetic.main.tab_content.view.*
import android.view.View.OnTouchListener
import com.example.graduationproject.api.donorApi.donationType.DonationTypeJson
import com.example.graduationproject.classes.TabLayoutSettings
import com.example.graduationproject.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(), CharitiesAdapter.onCharityItemClickListener {

    private lateinit var  charitiesList: MutableList<Charity>
    private val TAB_ICONS = arrayOf(
        R.drawable.all,
        R.drawable.money,
        R.drawable.food,
        R.drawable.clothes,
    )

    private var TAB_LABEL = arrayOf<String>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        requireActivity().nav_bottom.visibility=View.VISIBLE

        getDonationType()

        val sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)
        sectionsPagerAdapter.addFragments(AllDonationFragment())
        sectionsPagerAdapter.addFragments(MoneyDonationFragment())
        sectionsPagerAdapter.addFragments(FoodDonationFragment())
        sectionsPagerAdapter.addFragments(ClothesDonationFragment())
        root.campaigns_viewpager.adapter = sectionsPagerAdapter
        root.donor_home_tab_layout.setupWithViewPager(root.campaigns_viewpager)

        var tabLayout = TabLayoutSettings()
        tabLayout.setupTabIcons(root.donor_home_tab_layout,TAB_ICONS)
        tabLayout.setupTabLabels(root.donor_home_tab_layout, TAB_LABEL)
        tabLayout.setTabMargin(root.donor_home_tab_layout,10,10,100)

        root.search.setOnSearchClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, SearchResultsFragment()).addToBackStack(null).commit()
            requireActivity().nav_bottom.visibility=View.GONE
        }


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
                        TAB_LABEL.plus(donation_type.name)
                        Log.e("tab labels", donation_type.name)
                    }
                    TabLayoutSettings().setupTabLabels(donor_home_tab_layout, TAB_LABEL)

                } else {
                    Log.e("errorBody", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<DonationTypeJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
    }

}