package com.example.graduationproject.charity.fragments

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.graduationproject.R
import com.example.graduationproject.adapters.MyPagerAdapter
import kotlinx.android.synthetic.main.activity_charity_main.*
import kotlinx.android.synthetic.main.fragment_add_campaign.*
import kotlinx.android.synthetic.main.fragment_add_campaign.view.*
import java.util.*

class AddCampaignFragment : Fragment() {

    lateinit var layouts: IntArray
    lateinit var pagerAdapter: MyPagerAdapter
     var donationType:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val languageToLoad = "ar"

        val locale = Locale(languageToLoad)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(
            config,
            resources.displayMetrics
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_add_campaign, container, false)

         var arg = requireArguments().getString("donationType")
            donationType = if (arg =="food"){
                "food"
            }else{
                "clothes"
            }
        layouts = intArrayOf(R.layout.add_campaign_phase_one, R.layout.add_campaign_phase_two, R.layout.add_campaign_phase_three,R.layout.add_campaign_phase_four)
        pagerAdapter = MyPagerAdapter(requireContext(), layouts)
        root.add_campaign_pager.adapter = pagerAdapter

        root.add_campaign_pager.rotationY = 180F
        requireActivity().charity_nav_bottom.visibility=View.GONE

        root.add_campaign_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                if (position == layouts.size - 1) {
                    //LAST PAGE
                    root.btn_txt.text = "اضافة حملة"
                    root.btn_img.visibility = View.GONE

                } else {
                    root.btn_txt.text = "التالي"
                    root.btn_img.visibility = View.VISIBLE
                }
            }

        })
        root.add_campaign_next.setOnClickListener {
            val currentPage = add_campaign_pager.currentItem + 1
            if (currentPage < layouts.size) {
                //move to next page
                add_campaign_pager.currentItem = currentPage
            } else {
                var bundle = Bundle()
                bundle.putBoolean("addCampaign",true)
                var fragment = HomeFragment()
                fragment.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.charityContainer,fragment).addToBackStack(null).commit()
            }
        }

        return root
    }


    override fun onResume() {
        super.onResume()
        requireActivity().charity_nav_bottom.visibility=View.GONE
    }

}