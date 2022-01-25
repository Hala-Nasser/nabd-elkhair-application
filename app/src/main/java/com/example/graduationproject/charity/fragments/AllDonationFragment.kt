package com.example.graduationproject.charity.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.donor.DonorMainActivity
import com.example.graduationproject.donor.adapters.CampaignsAdapter
import com.example.graduationproject.donor.adapters.DonationTypeAdapter
import com.example.graduationproject.donor.fragments.CampaignDetailsFragment
import com.example.graduationproject.donor.models.Campaigns
import com.example.graduationproject.donor.models.Charity
import com.example.graduationproject.donor.models.DonationType
import kotlinx.android.synthetic.main.activity_donor_main.*
import kotlinx.android.synthetic.main.fragment_all_donation.view.*
import kotlinx.android.synthetic.main.fragment_clothes_donation.view.*
import androidx.core.app.ShareCompat.getCallingActivity





class AllDonationFragment : Fragment(), CampaignsAdapter.onCampaignItemClickListener {
    private lateinit var  campaignsList: MutableList<Campaigns>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_all_donation, container, false)
        campaignsList = mutableListOf()
        campaignsList.add(Campaigns(R.drawable.campaign_image,"تقديم الأكل للعائلات المحتاجة","22/2/2022"
            , "لوريم ايبسوم هو نموذج افتراضي يوضع في التصاميم لتعرض على العميل ليتصور طريقه وضع النصوص بالتصاميم سواء كانت تصاميم مطبوعه ... بروشور او فلاير على سبيل المثال ... او نماذج مواقع انترنت \n", DonationType(R.drawable.clothes), Charity(R.drawable.charity_image,"جمعية الاحسان الخيرية","فلسطين, غزة")
        ))
        campaignsList.add(Campaigns(R.drawable.campaign_image,"تقديم الأكل للعائلات المحتاجة","22/2/2022"
            , "لوريم ايبسوم هو نموذج افتراضي يوضع في التصاميم لتعرض على العميل ليتصور طريقه وضع النصوص بالتصاميم سواء كانت تصاميم مطبوعه ... بروشور او فلاير على سبيل المثال ... او نماذج مواقع انترنت \n", DonationType(R.drawable.clothes), Charity(R.drawable.charity_image,"جمعية الاحسان الخيرية","فلسطين, غزة")
        ))
        campaignsList.add(Campaigns(R.drawable.campaign_image,"تقديم الأكل للعائلات المحتاجة","22/2/2022"
            , "لوريم ايبسوم هو نموذج افتراضي يوضع في التصاميم لتعرض على العميل ليتصور طريقه وضع النصوص بالتصاميم سواء كانت تصاميم مطبوعه ... بروشور او فلاير على سبيل المثال ... او نماذج مواقع انترنت \n", DonationType(R.drawable.clothes), Charity(R.drawable.charity_image,"جمعية الاحسان الخيرية","فلسطين, غزة")
        ))

        if (activity!!::class.java.name == DonorMainActivity::class.java.name){
            root.rv_all_donation.layoutManager = LinearLayoutManager(activity,RecyclerView.HORIZONTAL,false)
            root.rv_all_donation.setHasFixedSize(true)
            val campaignsAdapter =
                CampaignsAdapter(this.activity, campaignsList,"DonorHome",this)
            root.rv_all_donation.adapter = campaignsAdapter
        }else{
            root.rv_all_donation.layoutManager = GridLayoutManager(this.context,2)
            root.rv_all_donation.setHasFixedSize(true)
            val campaignsAdapter =
                CampaignsAdapter(this.activity, campaignsList,"CharityHome", this)
            root.rv_all_donation.adapter = campaignsAdapter
        }

        return root
    }

    override fun onItemClick(data: Campaigns, position: Int) {

        val fragment = CampaignDetailsFragment()
        val b=Bundle()
        b.putString("campaign_name",data.campaignName)
        b.putInt("campaign_image",data.campaignImg!!)
        b.putString("campaign_date",data.campaignDate)
        b.putParcelable("campaign_charity",data.campaignCharity)

        fragment.arguments=b

        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer,fragment).addToBackStack(null).commit()
        requireActivity().nav_bottom.visibility=View.GONE
    }

}