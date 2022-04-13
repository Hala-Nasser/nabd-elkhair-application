package com.example.graduationproject.donor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.charity.models.Donation
import com.example.graduationproject.donor.adapters.CampaignsAdapter
import com.example.graduationproject.donor.models.Campaigns
import com.example.graduationproject.donor.models.Charity
import com.example.graduationproject.donor.models.DonationType
import com.example.graduationproject.donor.models.Donor
import kotlinx.android.synthetic.main.activity_donor_main.*
import kotlinx.android.synthetic.main.fragment_charity_campaigns.view.*

class CharityCampaignsFragment : Fragment(), CampaignsAdapter.onCampaignItemClickListener {

    private lateinit var campaignsList: MutableList<Campaigns>
    private var donationList = ArrayList<Donation>()
    private var donationList1 = ArrayList<Donation>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_charity_campaigns, container, false)


        var donor1 = Donor(null,null,"Mary Ann Vargas","غزة",null,null,null,null)
        var donor2 = Donor(null,null,"Mary Ann Vargas","غزة",null,null,null,null)
        var donor3 = Donor(null,null,"Mary Ann Vargas","غزة",null,null,null,null)

        donationList1.add(Donation(donor1,"1","025896542","غزة","غزة","غزة","200 شيكل"))


        donationList.add(Donation(donor1,"1","025896542","غزة","غزة","غزة","200 شيكل"))
        donationList.add(Donation(donor2,"2","025896542","غزة","غزة","غزة","200 شيكل"))
        donationList.add(Donation(donor3,"3","025896542","غزة","غزة","غزة","200 شيكل"))


        var cam1 =Campaigns("1",R.drawable.campaign_image,"تقديم الأكل للعائلات المحتاجة للفقراء و المساكين","22/2/2022","05:00"
            , "لوريم ايبسوم هو نموذج افتراضي يوضع في التصاميم لتعرض على العميل ليتصور طريقه وضع النصوص بالتصاميم سواء كانت تصاميم مطبوعه ... بروشور او فلاير على سبيل المثال ... او نماذج مواقع انترنت \n", DonationType(R.drawable.food,"طعام"), Charity(R.drawable.charity_image,"جمعية الاحسان الخيرية","فلسطين, غزة")
            ,donationList1
        )

        var cam2 =  Campaigns("2",R.drawable.campaign_image,"تقديم الملابس للعائلات المحتاجة","22/2/2022","05:00"
            , "لوريم ايبسوم هو نموذج افتراضي يوضع في التصاميم لتعرض على العميل ليتصور طريقه وضع النصوص بالتصاميم سواء كانت تصاميم مطبوعه ... بروشور او فلاير على سبيل المثال ... او نماذج مواقع انترنت \n", DonationType(R.drawable.clothes,"ملابس"), Charity(R.drawable.charity_image,"جمعية الاحسان الخيرية","فلسطين, غزة")
            ,null )
        var cam3 = Campaigns("3",R.drawable.campaign_image,"تقديم المال للعائلات المحتاجة","22/2/2022","05:00"
            , "لوريم ايبسوم هو نموذج افتراضي يوضع في التصاميم لتعرض على العميل ليتصور طريقه وضع النصوص بالتصاميم سواء كانت تصاميم مطبوعه ... بروشور او فلاير على سبيل المثال ... او نماذج مواقع انترنت \n", DonationType(R.drawable.money,"مال"), Charity(R.drawable.charity_image,"جمعية الاحسان الخيرية","فلسطين, غزة")
            ,donationList
        )
        campaignsList = mutableListOf()
        campaignsList.add(cam1)
        campaignsList.add(cam2)
        campaignsList.add(cam3)

        if (campaignsList.isEmpty()) {
            root.no_campaign.visibility = View.VISIBLE
            root.rv_campaigns.visibility = View.GONE

        } else {
            root.no_campaign.visibility = View.GONE
            root.rv_campaigns.visibility = View.VISIBLE

            root.rv_campaigns.layoutManager = LinearLayoutManager(
                activity,
                RecyclerView.VERTICAL, false
            )
            root.rv_campaigns.setHasFixedSize(true)
            val campaignsAdapter =
                CampaignsAdapter(this.activity, campaignsList, "CharityCampaigns", this)
            root.rv_campaigns.adapter = campaignsAdapter

        }


        return root
    }

    override fun onItemClick(data: Campaigns, position: Int, from: String) {
        val fragment = CampaignDetailsFragment()
        val b = Bundle()
        b.putString("campaign_name", data.campaignName)
        b.putInt("campaign_image", data.campaignImg!!)
        b.putString("campaign_date", data.campaignDate)
        b.putParcelable("campaign_charity", data.campaignCharity)
        b.putParcelable("campaign_donation_type", data.campaignDonationType)

        fragment.arguments = b

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, fragment).addToBackStack(null).commit()
        requireActivity().nav_bottom.visibility=View.GONE
    }

}