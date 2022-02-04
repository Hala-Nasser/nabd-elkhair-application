package com.example.graduationproject.charity.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.graduationproject.R
import com.example.graduationproject.adapters.MyPagerAdapter
import kotlinx.android.synthetic.main.activity_charity_main.*
import kotlinx.android.synthetic.main.add_campaign_phase_one.*
import kotlinx.android.synthetic.main.add_campaign_phase_one.view.*
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
                root.progressBar.progress += 25
                when (position) {
                    layouts.size - 1 -> {
                        //LAST PAGE
                        changeProgressData(root,"اضافة حملة",View.GONE,"4 من 4","تاكيد الحملة","")
                    }
                    0 -> {
                        changeProgressData(root,"التالي",View.VISIBLE,"1 من 4","معلومات الحملة","التالي : تاريخ انتهاء الحملة")
                       // Log.d("data",campaign_title.text.toString())
                    }
                    1 -> {

                        changeProgressData(root,"التالي",View.VISIBLE,"2 من 4","تاريخ انتهاء الحملة","التالي : إضافة وقت الانتهاء")

                    }
                    2 -> {

                        changeProgressData(root,"التالي",View.VISIBLE,"3 من 4","إضافة وقت الانتهاء","التالي : تاكيد الحملة")

                    }
                }

            }

        })
        root.add_campaign_next.setOnClickListener {
            val currentPage = root.add_campaign_pager.currentItem + 1

            if (root.add_campaign_pager.currentItem==0){
                Log.d("data",campaign_title.text.toString())
            }
            if (currentPage < layouts.size) {
                //move to next page
                root.add_campaign_pager.currentItem = currentPage
                root.progressBar.progress += 25
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

    private fun changeProgressData(root: View,
                                   btn_txt:String,btn_img:Int,
                                   progress_rate:String,title:String,subtitle:String) {
        root.btn_txt.text = btn_txt
        root.btn_img.visibility = btn_img
        root.progress_rate.text = progress_rate
        root.add_campaign_title.text = title
        root.add_campaign_subtitle.text = subtitle
    }

    override fun onResume() {
        super.onResume()
        requireActivity().charity_nav_bottom.visibility=View.GONE
    }

}