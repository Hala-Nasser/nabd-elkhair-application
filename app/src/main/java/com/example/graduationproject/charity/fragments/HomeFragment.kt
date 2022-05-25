package com.example.graduationproject.charity.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.graduationproject.R
import kotlinx.android.synthetic.main.activity_charity_main.*
import kotlinx.android.synthetic.main.fragment_charity_home.*
import kotlinx.android.synthetic.main.campaign_added_dialog.view.*
import kotlinx.android.synthetic.main.fragment_charity_home.view.*
import android.os.Build
import android.util.Log
import android.view.MenuItem
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentTransaction
import com.example.graduationproject.api.donorApi.donationType.DonationTypeJson
import com.example.graduationproject.classes.TabLayoutSettings
import com.example.graduationproject.donor.adapters.PageAdapterDonationType
import com.example.graduationproject.donor.fragments.CampaignsAccordingToDonationTypeFragment
import com.example.graduationproject.network.RetrofitInstance
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.nambimobile.widgets.efab.FabOption
import com.nambimobile.widgets.efab.Orientation
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.android.synthetic.main.donation_type_tab_item.view.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(){
     var addCampaign = false
    var donation_type_ids = ArrayList<Int>()
    lateinit var sharedPref : SharedPreferences
    lateinit var editor : SharedPreferences.Editor

    @SuppressLint("ClickableViewAccessibility", "UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_charity_home, container, false)

        requireActivity().charity_nav_bottom.visibility=View.VISIBLE

        sharedPref= requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        editor = sharedPref.edit()

        if (arguments!=null) {
             addCampaign = requireArguments().getBoolean("addCampaign")
            if (addCampaign) {
                getDialog()
            }else{
               // requireActivity().charity_nav_bottom.alpha = 1f
               // root.home_expandable_layout.isExpanded = false
            }
        }
        getDonationType()

         if (root.expandable_fab_layout.isOpen()){
             root.expandable_fab.efabColor = resources.getColor(R.color.white)
         }

        root.donation_requests_image.setOnClickListener {
            var fragment = DonationRequestsFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.charityContainer, fragment).addToBackStack(null).commit()
        }

        //root.campaign_viewpager.currentItem = 0
        root.charity_home_tab_layout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
//                Log.e("on select tab", "enter")
//                var mSelectedPosition = root.charity_home_tab_layout.selectedTabPosition
//                editor.putInt("selected charity home donation type", donation_type_ids[mSelectedPosition])
//                editor.putBoolean("isDonor", false)
//                editor.apply()
               // val donation_type = sharedPref.getInt("selected char
                // ity home donation type", 0)

                root.campaign_viewpager.currentItem = tab.position


//                Log.e("donation type", mSelectedPosition.toString())
//                val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
//                if (Build.VERSION.SDK_INT >= 26) {
//                    transaction.setReorderingAllowed(false)
//                }
//
//                transaction.detach(CampaignsAccordingToDonationTypeFragment()).attach(
//                    CampaignsAccordingToDonationTypeFragment()
//                ).commit()

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


        return root
    }


    fun getDonationType() {

        var sharedPref= requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val id = sharedPref.getInt("charity_id", 0)
        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getCharityDonationTypes(id)

        response.enqueue(object : Callback<DonationTypeJson> {
            override fun onResponse(call: Call<DonationTypeJson>, response: Response<DonationTypeJson>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data

                    donation_type_ids.add(0)
                    charity_home_tab_layout.addTab(
                        charity_home_tab_layout.newTab().
                        setCustomView(createTabItemView("","")),true)

                    for (donation_type in data){
                        donation_type_ids.add(donation_type.id)
                        charity_home_tab_layout.addTab(charity_home_tab_layout.newTab().setCustomView(createTabItemView(RetrofitInstance.IMAGE_URL+donation_type.image,donation_type.name)),false)
                        val option = FabOption(expandable_fab_layout.context, Orientation.PORTRAIT)
                        option.id = donation_type.id
                        option.setOnClickListener {
                            var bundle = Bundle()
                            bundle.putString("donationType", option.id.toString())
                            var fragment = AddCampaignFragment()
                            fragment.arguments = bundle
                            requireActivity().supportFragmentManager.beginTransaction()
                                .replace(R.id.charityContainer, fragment).addToBackStack(null).commit()
                        }
                        option.fabOptionIcon = resources.getDrawable(R.drawable.ic_arrow_up)
                        option.fabOptionColor = resources.getColor(R.color.app_color)
                        option.label.labelText = donation_type.name
                        //Picasso.get().load(RetrofitInstance.IMAGE_URL+donation_type.image).into(option.fabOptionIcon)
                        expandable_fab_layout.addView(option)
//                        Log.e("options",option.id.toString())
                    }
                    TabLayoutSettings().setTabMargin(charity_home_tab_layout, 10, 10, 100)
//                        Log.e("donation_type_ids", donation_type_ids.size.toString())
//                        editor.putInt("selected charity home donation type", donation_type_ids[0])
//                        editor.putBoolean("isDonor", false)
//                        editor.apply()

                    // Create adapter after adding the tabs
                    val adapter = PageAdapterDonationType(childFragmentManager,
                        charity_home_tab_layout.tabCount,
                        donation_type_ids)
                    campaign_viewpager.adapter = adapter
                    campaign_viewpager.offscreenPageLimit = 1
//                    charity_home_tab_layout.setupWithViewPager(campaign_viewpager)
                    campaign_viewpager.addOnPageChangeListener(
                        TabLayout.TabLayoutOnPageChangeListener(
                            charity_home_tab_layout
                        )
                    )
                    charity_home_tab_layout.tabMode = TabLayout.MODE_SCROLLABLE

                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<DonationTypeJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
    }


    fun getDialog(){
        var view= layoutInflater.inflate(R.layout.campaign_added_dialog,null)
        val campaignBottomSheet = BottomSheetDialog(this.requireContext())
        //campaignDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        campaignBottomSheet.setContentView(view)
        campaignBottomSheet.setCanceledOnTouchOutside(false)
        view.close_dialog.setOnClickListener {
            campaignBottomSheet.dismiss()
        }
        view.home_dialog_btn.setOnClickListener {
            campaignBottomSheet.dismiss()
        }
        campaignBottomSheet.show()
    }

    private fun createTabItemView(imgUri: String,txt:String): View? {
        val v: View = LayoutInflater.from(context).inflate(R.layout.donation_type_tab_item, null)
        if (imgUri==""){
            v.donation_type_image.setImageResource(R.drawable.all)
        }else{
            Picasso.get().load(imgUri).into(v.donation_type_image)
        }
        v.donation_type_title.text = txt
        return v
    }


}