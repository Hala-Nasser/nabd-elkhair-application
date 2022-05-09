package com.example.graduationproject.charity.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.graduationproject.R
import com.example.graduationproject.adapters.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_charity_complete_signup.*
import kotlinx.android.synthetic.main.activity_charity_main.*
import kotlinx.android.synthetic.main.activity_donor_main.*
import kotlinx.android.synthetic.main.fragment_charity_home.*
import kotlinx.android.synthetic.main.tab_content.*
import kotlinx.android.synthetic.main.tab_content.view.*
import android.view.View.OnTouchListener
import kotlinx.android.synthetic.main.campaign_added_dialog.view.*
import kotlinx.android.synthetic.main.fragment_charity_home.view.*
import android.content.res.ColorStateList
import android.os.Build
import android.util.Log
import androidx.fragment.app.FragmentTransaction
import com.example.graduationproject.api.donorApi.donationType.DonationTypeJson
import com.example.graduationproject.classes.TabLayoutSettings
import com.example.graduationproject.donor.adapters.PageAdapterDonationType
import com.example.graduationproject.donor.fragments.CampaignsAccordingToDonationTypeFragment
import com.example.graduationproject.network.RetrofitInstance
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_charity_main.view.*
import kotlinx.android.synthetic.main.fragment_charity_profile.view.*
import kotlinx.android.synthetic.main.fragment_donation.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import net.cachapa.expandablelayout.ExpandableLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(){

        private val TAB_ICONS = arrayOf(
            R.drawable.all,
            R.drawable.money,
            R.drawable.food,
            R.drawable.clothes,
        )
     var addCampaign = false
    var donation_type_ids = ArrayList<Int>()
    lateinit var sharedPref : SharedPreferences
    lateinit var editor : SharedPreferences.Editor

    @SuppressLint("ClickableViewAccessibility")
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
                requireActivity().charity_nav_bottom.alpha = 1f
                root.home_expandable_layout.isExpanded = false
            }
        }

        root.donation_requests_image.setOnClickListener {
            var fragment = DonationRequestsFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.charityContainer, fragment).addToBackStack(null).commit()
        }

        getDonationType()
//        val sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)
//        sectionsPagerAdapter.addFragments(AllDonationFragment())
//        sectionsPagerAdapter.addFragmentsAndTitles(MoneyDonationFragment(),"مال")
//        sectionsPagerAdapter.addFragmentsAndTitles(FoodDonationFragment(),"طعام")
//        sectionsPagerAdapter.addFragmentsAndTitles(ClothesDonationFragment(),"ملابس")
//        root.campaign_viewpager.adapter = sectionsPagerAdapter
//        root.charity_home_tab_layout.setupWithViewPager(root.campaign_viewpager)
//
//        var tabLayout = TabLayoutSettings()
//        tabLayout.setupTabIcons(root.charity_home_tab_layout,TAB_ICONS)
//        tabLayout.setTabMargin(root.charity_home_tab_layout,10,10,100)

//        root.campaign_viewpager.setOnTouchListener(OnTouchListener { v, event ->
//            when (root.campaign_viewpager.currentItem) {
//                0 -> {
//                    root.campaign_viewpager.setCurrentItem(-1, false)
//                    return@OnTouchListener true
//                }
//                1 -> {
//                    root.campaign_viewpager.setCurrentItem(1-1, false)
//                    root.campaign_viewpager.setCurrentItem(1, false)
//                    return@OnTouchListener true
//                }
//                2 -> {
//                    root.campaign_viewpager.setCurrentItem(2-1, false)
//                    root.campaign_viewpager.setCurrentItem(2, false)
//                    return@OnTouchListener true
//                }
//                3 -> {
//                    root.campaign_viewpager.setCurrentItem(3-1, false)
//                    root.campaign_viewpager.setCurrentItem(3, false)
//                    return@OnTouchListener true
//                }
//                else -> true
//            }
//        })

        getExpandedfab(root)

        root.charity_home_tab_layout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.e("on select tab", "enter")
                var mSelectedPosition = root.charity_home_tab_layout.selectedTabPosition
                editor.putInt("selected home donation type", donation_type_ids[mSelectedPosition])
                editor.apply()
                val donation_type = sharedPref.getInt("selected home donation type", 0)

                root.campaign_viewpager.currentItem = tab.position


                Log.e("donation type", donation_type.toString())
                val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                if (Build.VERSION.SDK_INT >= 26) {
                    transaction.setReorderingAllowed(false)
                }

                transaction.detach(CampaignsAccordingToDonationTypeFragment()).attach(
                    CampaignsAccordingToDonationTypeFragment()
                ).commit()

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


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


                    for (donation_type in data){
                        donation_type_ids.add(donation_type.id)
                        charity_home_tab_layout.addTab(charity_home_tab_layout.newTab().setText(donation_type.name))
                    }
                    TabLayoutSettings().setTabMargin(charity_home_tab_layout, 10, 10, 100)
//                    Log.e("donation_type_ids", donation_type_ids.size.toString())
//                    editor.putInt("selected home donation type", donation_type_ids[0])
//                    editor.apply()

                    // Create adapter after adding the tabs
                    val adapter = PageAdapterDonationType(childFragmentManager, donation_type_ids.size)
                    campaign_viewpager.adapter = adapter
                    campaign_viewpager.addOnPageChangeListener(
                        TabLayout.TabLayoutOnPageChangeListener(
                            charity_home_tab_layout
                        )
                    )

                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<DonationTypeJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
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


}