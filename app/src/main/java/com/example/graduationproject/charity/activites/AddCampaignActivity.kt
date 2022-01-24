package com.example.graduationproject.charity.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.graduationproject.ChoiceActivity
import com.example.graduationproject.R
import com.example.graduationproject.adapters.MyPagerAdapter
import com.example.graduationproject.classes.GeneralChanges
import kotlinx.android.synthetic.main.activity_add_campaign.*

class AddCampaignActivity : AppCompatActivity() {
    lateinit var layouts: IntArray
    lateinit var pagerAdapter: MyPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_campaign)
        GeneralChanges().setStatusBarTransparent(this)
        GeneralChanges().fadeTransition(this)


        layouts = intArrayOf(R.layout.add_campaign_phase_one, R.layout.add_campaign_phase_one, R.layout.add_campaign_phase_one)
        pagerAdapter = MyPagerAdapter(applicationContext, layouts)
        add_campaign_pager.adapter = pagerAdapter

        add_campaign_pager.rotationY = 180F

        add_campaign_next.setOnClickListener {
            val currentPage = add_campaign_pager.currentItem + 1
            if (currentPage < layouts.size) {
                //move to next page
                add_campaign_pager.currentItem = currentPage
            } else {

               // GeneralChanges().prepareFadeTransition(this, ChoiceActivity())
            }
        }
    }
}