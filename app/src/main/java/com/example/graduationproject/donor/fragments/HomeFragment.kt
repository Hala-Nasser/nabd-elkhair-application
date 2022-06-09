package com.example.graduationproject.donor.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
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
import com.example.graduationproject.models.Charity
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
import com.example.graduationproject.api.donorApi.charities.CharitiesJson
import com.example.graduationproject.api.donorApi.charities.Data
import android.graphics.drawable.Drawable
import android.os.Parcelable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.graduationproject.api.donorApi.campaignAccordingToDonationType.CampaignsDonationTypeJson
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.donor.adapters.CampaignsAdapter
import com.example.graduationproject.models.Campaigns
import kotlinx.android.synthetic.main.donation_type_tab_item.view.*
import kotlinx.android.synthetic.main.fragment_capmaigns_according_to_donation_type.*


class HomeFragment : Fragment(), CharitiesAdapter.onCharityItemClickListener {

    var donation_type_ids = ArrayList<Int>()
    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        requireActivity().nav_bottom.visibility = View.VISIBLE

        sharedPref = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        editor = sharedPref.edit()
        val user_image = sharedPref.getString("user_image", "")
        Log.e("image", user_image!!)

        Picasso.get().load(RetrofitInstance.IMAGE_URL + user_image).into(root.profile_image)

        getDonationType()
        getCharities()

        root.campaigns_viewpager.isSaveEnabled = false
        root.search.setOnSearchClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, SearchResultsFragment()).addToBackStack(null).commit()
            requireActivity().nav_bottom.visibility = View.GONE
        }


        root.donor_home_tab_layout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                root.campaigns_viewpager.currentItem = tab.position
                SetOnSelectView(root.donor_home_tab_layout,tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                SetUnSelectView(root.donor_home_tab_layout,tab.position)
            }
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        return root
    }


    override fun onItemClick(data: Charity, position: Int) {
        val fragment = CharityDetailsFragment()
        val b = Bundle()
        b.putInt("charity_id", data.id)
        b.putString("charity_name", data.name)
        b.putString("charity_image", data.image)
        b.putString("charity_address", data.address)
        b.putString("charity_description", data.about)
        //b.putStringArrayList("charity_donation_type", data.donationTypes as  ArrayList<String>)

        b.putInt("charity_phone", data.phone)

        fragment.arguments = b

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, fragment).addToBackStack(null).commit()
        requireActivity().nav_bottom.visibility = View.GONE
    }

    fun getDonationType() {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getDonationType()

        response.enqueue(object : Callback<DonationTypeJson> {
            override fun onResponse(
                call: Call<DonationTypeJson>,
                response: Response<DonationTypeJson>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data

                    donation_type_ids.add(0)
                    donor_home_tab_layout.addTab(
                        donor_home_tab_layout.newTab().setCustomView(createTabItemView("", "")),
                        true
                    )

                    for (donation_type in data) {
                        donation_type_ids.add(donation_type.id)
                        donor_home_tab_layout.addTab(
                            donor_home_tab_layout.newTab().setCustomView(
                                createTabItemView(
                                    RetrofitInstance.IMAGE_URL + donation_type.image,
                                    donation_type.name
                                )
                            ), false
                        )
                    }

                    TabLayoutSettings().setTabMargin(donor_home_tab_layout, 10, 10, 100)
                    Log.e("donation_type_ids", donation_type_ids.size.toString())

                    val adapter = PageAdapterDonationType(
                        childFragmentManager,
                        donor_home_tab_layout.tabCount,
                        donation_type_ids
                    )
                    campaigns_viewpager.adapter = adapter
                    campaigns_viewpager.offscreenPageLimit = 1
                    campaigns_viewpager.addOnPageChangeListener(
                        TabLayoutOnPageChangeListener(
                            donor_home_tab_layout
                        )
                    )

                    donor_home_tab_layout.tabMode = TabLayout.MODE_SCROLLABLE

                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<DonationTypeJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
    }


    fun getCharities() {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getCharities()

        response.enqueue(object : Callback<CharitiesJson> {
            override fun onResponse(call: Call<CharitiesJson>, response: Response<CharitiesJson>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data

                    rv_all_charities.layoutManager =
                        LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                    rv_all_charities.setHasFixedSize(true)
                    val charitiesAdapter =
                        CharitiesAdapter(activity, data, this@HomeFragment)
                    rv_all_charities.adapter = charitiesAdapter

                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<CharitiesJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
    }

    private fun createTabItemView(imgUri: String, txt: String): View? {
        val v: View = LayoutInflater.from(context).inflate(R.layout.donation_type_tab_item, null)
        if (imgUri == "") {
            v.donation_type_image.setImageResource(R.drawable.all)
        } else {
            Picasso.get().load(imgUri).into(v.donation_type_image)
        }
        v.donation_type_title.text = txt
        return v
    }

    fun SetOnSelectView(tabLayout: TabLayout, position: Int) {
        val tab = tabLayout.getTabAt(position)
        val selected = tab!!.customView
        val iv_text = selected!!.findViewById<View>(R.id.donation_type_title) as TextView
        val iv_img = selected.findViewById<View>(R.id.donation_type_image) as ImageView
        iv_text.setTextColor(resources.getColor(R.color.white))
        DrawableCompat.setTint(
            DrawableCompat.wrap(iv_img.drawable),
            ContextCompat.getColor(requireActivity(), R.color.white)
        )
    }

    fun SetUnSelectView(tabLayout: TabLayout, position: Int) {
        val tab = tabLayout.getTabAt(position)
        val selected = tab!!.customView
        val iv_text = selected!!.findViewById<View>(R.id.donation_type_title) as TextView
        iv_text.setTextColor(resources.getColor(R.color.black))
        val iv_img = selected.findViewById<View>(R.id.donation_type_image) as ImageView
        DrawableCompat.setTint(
            DrawableCompat.wrap(iv_img.drawable),
            ContextCompat.getColor(requireActivity(), R.color.black)
        )
    }
}