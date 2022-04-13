package com.example.graduationproject.charity.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.charity.models.Donation
import com.example.graduationproject.donor.DonorMainActivity
import com.example.graduationproject.donor.adapters.CampaignsAdapter
import com.example.graduationproject.donor.fragments.CampaignDetailsFragment
import com.example.graduationproject.donor.fragments.ProfileFragment
import com.example.graduationproject.donor.models.Campaigns
import com.example.graduationproject.donor.models.Charity
import com.example.graduationproject.donor.models.DonationType
import com.example.graduationproject.donor.models.Donor
import kotlinx.android.synthetic.main.activity_charity_main.*
import kotlinx.android.synthetic.main.activity_donor_main.*
import kotlinx.android.synthetic.main.bottom_dialog_item.view.close
import kotlinx.android.synthetic.main.bottom_dialog_item_manual.view.*
import kotlinx.android.synthetic.main.fragment_clothes_donation.view.*



class ClothesDonationFragment : Fragment(), CampaignsAdapter.onCampaignItemClickListener {
    private lateinit var  campaignsList: MutableList<Campaigns>
    private  var  donationList = ArrayList<Donation>()
    private  var  donationList1 = ArrayList<Donation>()
    lateinit var dialog :Dialog
    lateinit var v :View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_clothes_donation, container, false)

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

        if (campaignsList.isEmpty()){
            root.clothes_no_campaign.visibility = View.VISIBLE
            root.rv_clothes_donation.visibility = View.GONE

        }else {
            root.clothes_no_campaign.visibility = View.GONE
            root.rv_clothes_donation.visibility = View.VISIBLE

            if (activity!!::class.java.name == DonorMainActivity::class.java.name) {

                if (requireParentFragment()::class.java.name == ProfileFragment()::class.java.name){
                    root.rv_clothes_donation.layoutManager = LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
                    root.rv_clothes_donation.setHasFixedSize(true)
                    val campaignsAdapter =
                        CampaignsAdapter(this.activity, campaignsList,"DonorProfile", this)
                    root.rv_clothes_donation.adapter = campaignsAdapter
                }else{
                    root.rv_clothes_donation.layoutManager = LinearLayoutManager(activity,RecyclerView.HORIZONTAL,false)
                    root.rv_clothes_donation.setHasFixedSize(true)
                    val campaignsAdapter =
                        CampaignsAdapter(this.activity, campaignsList,"DonorHome",this)
                    root.rv_clothes_donation.adapter = campaignsAdapter
                }
            } else {
                root.rv_clothes_donation.layoutManager = GridLayoutManager(this.context, 2)
                root.rv_clothes_donation.setHasFixedSize(true)
                val campaignsAdapter =
                    CampaignsAdapter(this.activity, campaignsList, "CharityHome", this)
                root.rv_clothes_donation.adapter = campaignsAdapter
            }
        }

        return root
    }

    override fun onItemClick(data: Campaigns, position: Int,from:String) {

        if (from=="DonorHome") {
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

        }else if (from == "DonorProfile"){
            showDialog()
            v.close.setOnClickListener {
                dialog.dismiss()
            }
        }else{
            val fragment = CharityCampaignDetailsFragment()
            val b = Bundle()
            b.putString("from", "ClothesDonation")
            b.putString("clothes_campaign_id", data.campaignId)
            b.putString("clothes_campaign_name", data.campaignName)
            b.putInt("clothes_campaign_image", data.campaignImg!!)
            b.putString("clothes_campaign_description", data.campaignDescription)
            b.putString("clothes_campaign_date", data.campaignDate)
            b.putString("clothes_campaign_time", data.campaignTime)
            b.putParcelableArrayList("clothes_campaign_donation", data.donation as ArrayList)

            fragment.arguments = b

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.charityContainer, fragment).addToBackStack(null).commit()
        }

    }

    private fun showDialog() {
        dialog = Dialog(this.requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        v= layoutInflater.inflate(R.layout.bottom_dialog_item_manual,null)
        dialog.setContentView(v)
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setGravity(Gravity.BOTTOM)
        dialog.setCancelable(false)

        v.title.text = "تفاصيل التبرع"
        v.amount_linear.visibility = View.GONE
        v.amount_view.visibility = View.GONE
        v.confirm.visibility = View.GONE

        dialog.show()

    }


}