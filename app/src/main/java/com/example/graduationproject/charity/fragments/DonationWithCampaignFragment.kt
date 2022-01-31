package com.example.graduationproject.charity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.charity.adapters.CampaignDonationAdapter
import com.example.graduationproject.charity.adapters.DonationAdapter
import com.example.graduationproject.charity.adapters.DonorsAdapter
import com.example.graduationproject.charity.models.Donation
import com.example.graduationproject.charity.models.Donor
import com.example.graduationproject.donor.models.Campaigns
import com.example.graduationproject.donor.models.Charity
import com.example.graduationproject.donor.models.DonationType
import kotlinx.android.synthetic.main.fragment_donation_with_campaign.view.*


class DonationWithCampaignFragment : Fragment() {

    private  var  donationList = ArrayList<Donation>()
    private  var  donationList1 = ArrayList<Donation>()
    private lateinit var  campaignsList: MutableList<Campaigns>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_donation_with_campaign, container, false)

        var donor1 = Donor(R.drawable.campaign_image,"Mary Ann Vargas","غزة")
        var donor2 = Donor(R.drawable.campaign_image,"Harry Ann Vargas","غزة")
        var donor3 = Donor(R.drawable.campaign_image,"Mary Ann Vargas","غزة")

        donationList1.add(Donation(donor1,"1","025896542","غزة","غزة","غزة","200 شيكل"))


        donationList.add(Donation(donor1,"1","025896542","غزة","غزة","غزة","200 شيكل"))
        donationList.add(Donation(donor2,"2","025896542","غزة","غزة","غزة","200 شيكل"))
        donationList.add(Donation(donor3,"3","025896542","غزة","غزة","غزة","200 شيكل"))


        var cam1 =Campaigns("1",R.drawable.campaign_image,"تقديم الأكل للعائلات المحتاجة","22/2/2022","05:00"
            , "لوريم ايبسوم هو نموذج افتراضي يوضع في التصاميم لتعرض على العميل ليتصور طريقه وضع النصوص بالتصاميم سواء كانت تصاميم مطبوعه ... بروشور او فلاير على سبيل المثال ... او نماذج مواقع انترنت \n", DonationType(R.drawable.clothes), Charity(R.drawable.charity_image,"جمعية الاحسان الخيرية","فلسطين, غزة")
        ,donationList1
        )

       var cam2 =  Campaigns("2",R.drawable.campaign_image,"تقديم الملابس للعائلات المحتاجة","22/2/2022","05:00"
            , "لوريم ايبسوم هو نموذج افتراضي يوضع في التصاميم لتعرض على العميل ليتصور طريقه وضع النصوص بالتصاميم سواء كانت تصاميم مطبوعه ... بروشور او فلاير على سبيل المثال ... او نماذج مواقع انترنت \n", DonationType(R.drawable.clothes), Charity(R.drawable.charity_image,"جمعية الاحسان الخيرية","فلسطين, غزة")
       ,null )
       var cam3 = Campaigns("3",R.drawable.campaign_image,"تقديم الدواء للعائلات المحتاجة","22/2/2022","05:00"
            , "لوريم ايبسوم هو نموذج افتراضي يوضع في التصاميم لتعرض على العميل ليتصور طريقه وضع النصوص بالتصاميم سواء كانت تصاميم مطبوعه ... بروشور او فلاير على سبيل المثال ... او نماذج مواقع انترنت \n", DonationType(R.drawable.clothes), Charity(R.drawable.charity_image,"جمعية الاحسان الخيرية","فلسطين, غزة")
       ,donationList
       )

        campaignsList = mutableListOf()
        campaignsList.add(cam1)
        campaignsList.add(cam2)
        campaignsList.add(cam3)



        root.rv_donation_with_campaign.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL, false
        )
        root.rv_donation_with_campaign.setHasFixedSize(true)
        val donationAdapter =
            CampaignDonationAdapter(this.activity, campaignsList)
        root.rv_donation_with_campaign.adapter = donationAdapter
        return root

    }

}