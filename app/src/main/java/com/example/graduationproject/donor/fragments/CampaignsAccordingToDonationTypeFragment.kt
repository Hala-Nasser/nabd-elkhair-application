package com.example.graduationproject.donor.fragments

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.graduationproject.R
import android.widget.TextView

class CampaignsAccordingToDonationTypeFragment : Fragment(),
    CampaignsAdapter.onCampaignItemClickListener {

    var progressDialog: ProgressDialog? = null
    lateinit var dialog: BottomSheetDialog
    lateinit var v: View
    var donation_type = 0
    var token = ""

    companion object {
        fun newInstance(`val`: Int): CampaignsAccordingToDonationTypeFragment {
            val fragment = CampaignsAccordingToDonationTypeFragment()
            val args = Bundle()
            args.putInt("someInt", `val`)
            fragment.arguments = args
            return fragment
        }
    }


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

        root.all_no_campaign.visibility = View.GONE
        root.rv_campaigns.visibility = View.GONE

        val sharedPref = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        var isDonor = sharedPref.getBoolean("isDonor", false)
        token = sharedPref.getString("charity_token", "")!!


        Log.e("donation_in_campaign", donation_type.toString())

        progressDialog = ProgressDialog(activity)
        GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
        if (isDonor) {
            getCampaignsDonationType()
        } else getCharityCampaignsAccordingToDonationType()

        return root
    }

    fun getCharityCampaignsAccordingToDonationType() {
        val d = requireArguments().getInt("someInt", 0)
        val retrofitInstance =
            RetrofitInstance.create()
        val response =
            retrofitInstance.getCharityCampaignsAccordingToDonationType("Bearer $token", d)

        response.enqueue(object : Callback<CampaignsDonationTypeJson> {
            override fun onResponse(
                call: Call<CampaignsDonationTypeJson>,
                response: Response<CampaignsDonationTypeJson>
            ) {
                if (response.isSuccessful) {
                    var data = response.body()!!.data
                    Log.e("response", response.body().toString())
                    if (data.isEmpty()) {
                        all_no_campaign.visibility = View.VISIBLE
                        rv_campaigns.visibility = View.GONE
                        GeneralChanges().hideDialog(progressDialog!!)

                    } else {
                        all_no_campaign.visibility = View.GONE
                        rv_campaigns.visibility = View.VISIBLE
                        rv_campaigns.layoutManager = GridLayoutManager(requireContext(), 2)
                        rv_campaigns.setHasFixedSize(true)
                        val campaignsAdapter =
                            CampaignsAdapter(
                                activity,
                                data,
                                "CharityHome",
                                this@CampaignsAccordingToDonationTypeFragment
                            )
                        rv_campaigns.adapter = campaignsAdapter
                        campaignsAdapter.notifyDataSetChanged()
                        GeneralChanges().hideDialog(progressDialog!!)
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


    fun getCampaignsDonationType() {

        val d = requireArguments().getInt("someInt", 0)
        Log.e("donation_type_hala", d.toString())
        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getCampaignsAccordingToDonationType(d)

        response.enqueue(object : Callback<CampaignsDonationTypeJson> {
            override fun onResponse(
                call: Call<CampaignsDonationTypeJson>,
                response: Response<CampaignsDonationTypeJson>
            ) {
                if (response.isSuccessful) {
                    var data = response.body()!!.data
                    val adapter = CampaignsAdapter(
                        activity,
                        data,
                        "DonorProfile",
                        this@CampaignsAccordingToDonationTypeFragment
                    )
                    Log.e("response", response.body().toString())
                    if (data.isEmpty()) {
                        Log.e("get donation", "is empty")
                        all_no_campaign.visibility = View.VISIBLE
                        rv_campaigns.visibility = View.GONE
                        rv_campaigns.adapter = adapter
                        GeneralChanges().hideDialog(progressDialog!!)

                    } else {
                        Log.e("get donation", "is not empty")
                        all_no_campaign.visibility = View.GONE
                        rv_campaigns.visibility = View.VISIBLE

                        if (requireParentFragment()::class.java.name == ProfileFragment()::class.java.name) {
                            rv_campaigns.layoutManager = LinearLayoutManager(
                                activity,
                                RecyclerView.VERTICAL, false
                            )
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
                            rv_campaigns?.adapter?.notifyDataSetChanged()
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
                b.putParcelableArrayList(
                    "campaign_donation_type",
                    data.donation_type as ArrayList<DonationType>
                )

                fragment.arguments = b

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.mainContainer, fragment).addToBackStack(null).commit()
                requireActivity().nav_bottom.visibility = View.GONE

            }
            else -> {
                val fragment = CharityCampaignDetailsFragment()
                val b = Bundle()
                b.putInt("campaign_id", data.id)
                b.putString("campaign_name", data.name)
                b.putString("campaign_image", data.image)
                b.putString("campaign_description", data.description)
                b.putString("campaign_date", data.expiry_date)
                b.putString("campaign_time", data.expiry_time)
                b.putParcelableArrayList("campaign_donation", data.donation as ArrayList)

                fragment.arguments = b

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.charityContainer, fragment).addToBackStack(null).commit()
            }
        }
    }

}