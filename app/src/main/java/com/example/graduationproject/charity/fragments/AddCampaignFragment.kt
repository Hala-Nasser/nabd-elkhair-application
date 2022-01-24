package com.example.graduationproject.charity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.graduationproject.R
import com.example.graduationproject.adapters.MyPagerAdapter
import kotlinx.android.synthetic.main.fragment_add_campaign.*
import kotlinx.android.synthetic.main.fragment_add_campaign.view.*

class AddCampaignFragment : Fragment() {

    lateinit var layouts: IntArray
    lateinit var pagerAdapter: MyPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_add_campaign, container, false)

        layouts = intArrayOf(R.layout.add_campaign_phase_one, R.layout.add_campaign_phase_one, R.layout.add_campaign_phase_one)
        pagerAdapter = MyPagerAdapter(requireContext(), layouts)
        root.add_campaign_pager.adapter = pagerAdapter

        root.add_campaign_pager.rotationY = 180F

        root.add_campaign_next.setOnClickListener {
            val currentPage = add_campaign_pager.currentItem + 1
            if (currentPage < layouts.size) {
                //move to next page
                add_campaign_pager.currentItem = currentPage
            } else {

                // GeneralChanges().prepareFadeTransition(this, ChoiceActivity())
            }
        }

        return root
    }


}