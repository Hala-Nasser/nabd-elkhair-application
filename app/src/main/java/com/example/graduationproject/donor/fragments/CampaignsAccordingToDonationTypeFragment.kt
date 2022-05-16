package com.example.graduationproject.donor.fragments

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.api.donorApi.campaignAccordingToDonationType.CampaignsDonationTypeJson
import com.example.graduationproject.api.donorApi.campaignAccordingToDonationType.DonationType
import com.example.graduationproject.charity.fragments.CharityCampaignDetailsFragment
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.donor.adapters.CampaignsAdapter
import com.example.graduationproject.models.Campaigns
import com.example.graduationproject.network.RetrofitInstance
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_donor_main.*
import kotlinx.android.synthetic.main.bottom_dialog_item.view.*
import kotlinx.android.synthetic.main.bottom_dialog_item.view.close
import kotlinx.android.synthetic.main.bottom_dialog_item_manual.view.*
import kotlinx.android.synthetic.main.fragment_capmaigns_according_to_donation_type.*
import kotlinx.android.synthetic.main.fragment_capmaigns_according_to_donation_type.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class CampaignsAccordingToDonationTypeFragment : Fragment(),
    CampaignsAdapter.onCampaignItemClickListener {

    var progressDialog: ProgressDialog? = null
    lateinit var dialog: BottomSheetDialog
    lateinit var v: View
    var donation_type = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(
            R.layout.fragment_capmaigns_according_to_donation_type,
            container,
            false
        )

        Toast.makeText(activity, "reload", Toast.LENGTH_LONG).show()

        Log.e("campaign fragment", "enter")
        val sharedPref = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        donation_type = sharedPref.getInt("selected home donation type", 0)

        Log.e("donation in campaign", donation_type.toString())

        progressDialog = ProgressDialog(activity)
        GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
        getCampaignsDonationType(donation_type)

        return root
    }

    override fun onResume() {
        super.onResume()
        //getCampaignsDonationType(donation_type)
    }

    fun getCampaignsDonationType(donation_type: Int) {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getCampaignsAccordingToDonationType(donation_type)

        response.enqueue(object : Callback<CampaignsDonationTypeJson> {
            override fun onResponse(
                call: Call<CampaignsDonationTypeJson>,
                response: Response<CampaignsDonationTypeJson>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data
                    Log.e("response", response.body().toString())
                    Log.e("get donation", "is successful")
                    if (data.isEmpty()) {
                        Log.e("get donation", data.toString())
                        Log.e("get donation", "is empty")
                        all_no_campaign.visibility = View.VISIBLE
                        rv_campaigns.visibility = View.GONE
                        GeneralChanges().hideDialog(progressDialog!!)

                    } else {
                        Log.e("get donation", data.toString())
                        Log.e("get donation", "is not empty")
                        all_no_campaign.visibility = View.GONE
                        rv_campaigns.visibility = View.VISIBLE

                        if (requireParentFragment()::class.java.name == ProfileFragment()::class.java.name) {
                            rv_campaigns.layoutManager = LinearLayoutManager(
                                activity,
                                RecyclerView.VERTICAL, false
                            )
                            Log.e("data before adapter", data.toString())
                            rv_campaigns.setHasFixedSize(true)
                            val campaignsAdapter =
                                CampaignsAdapter(
                                    activity,
                                    data,
                                    "DonorProfile",
                                    this@CampaignsAccordingToDonationTypeFragment
                                )
                            rv_campaigns.adapter = campaignsAdapter
                            campaignsAdapter.notifyDataSetChanged()
                            GeneralChanges().hideDialog(progressDialog!!)
                        } else {
                            Log.e("data before adapter", data.toString())
                            rv_campaigns.layoutManager = LinearLayoutManager(
                                activity,
                                RecyclerView.HORIZONTAL, false
                            )
                            rv_campaigns.setHasFixedSize(true)
                            val campaignsAdapter =
                                CampaignsAdapter(
                                    activity,
                                    data,
                                    "DonorHome",
                                    this@CampaignsAccordingToDonationTypeFragment
                                )
                            rv_campaigns.adapter = campaignsAdapter
                            campaignsAdapter.notifyDataSetChanged()
                            GeneralChanges().hideDialog(progressDialog!!)
                        }
                    }

                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                    GeneralChanges().hideDialog(progressDialog!!)
                }

            }

            override fun onFailure(call: Call<CampaignsDonationTypeJson>, t: Throwable) {
                Log.e("failure", t.message!!)
                GeneralChanges().hideDialog(progressDialog!!)
            }
        })
    }

    override fun onItemClick(data: Campaigns, position: Int, from: String) {
        when (from) {
            "DonorHome" -> {
                val fragment = CampaignDetailsFragment()
                val b = Bundle()
                b.putInt("campaign_id", data.id)
                b.putString("campaign_name", data.name)
                b.putString("campaign_description", data.description)
                b.putString("campaign_image", data.image)
                b.putString("campaign_date", data.expiry_date)
                b.putParcelable("campaign_charity", data.charity)
                b.putParcelableArrayList("campaign_donation_type", data.donation_type as ArrayList<DonationType>)

                fragment.arguments = b

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.mainContainer, fragment).addToBackStack(null).commit()
                requireActivity().nav_bottom.visibility = View.GONE

            }
            "DonorProfile" -> {
                showDialog(data.donation_type[1], "electronic")
                v.close.setOnClickListener {
                    dialog.dismiss()
                }
            }
            else -> {
                val fragment = CharityCampaignDetailsFragment()
                val b = Bundle()
                b.putString("from", "AllDonation")
                b.putInt("all_campaign_id", data.id)
                b.putString("all_campaign_name", data.name)
                b.putString("all_campaign_image", data.image)
                b.putString("all_campaign_description", data.description)
                b.putString("all_campaign_date", data.expiry_date)
                b.putString("all_campaign_time", data.expiry_time)
                //b.putParcelableArrayList("all_campaign_donation", data.donation as ArrayList)

                fragment.arguments = b

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.charityContainer, fragment).addToBackStack(null).commit()
            }
        }
    }

    private fun showDialog(donationType: DonationType, donationMethod: String) {
        dialog = BottomSheetDialog(this.requireContext())
        if (donationType.name == "مال" && donationMethod == "electronic") {
            v = layoutInflater.inflate(R.layout.bottom_dialog_item, null)
            v.title_electronic.text = "تفاصيل التبرع"
//            v.confirm_electronic.visibility = View.GONE

        } else {
            v = layoutInflater.inflate(R.layout.bottom_dialog_item_manual, null)
            if (donationType.name == "مال") {
                v.amount_linear.visibility = View.GONE
                v.amount_view.visibility = View.GONE

            }
            v.title.text = "تفاصيل التبرع"
            v.confirm.visibility = View.GONE
        }

        dialog.setContentView(v)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

    }

}