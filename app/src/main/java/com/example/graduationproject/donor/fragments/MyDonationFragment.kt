package com.example.graduationproject.donor.fragments

import android.app.ProgressDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.api.donorApi.mydonations.Data
import com.example.graduationproject.api.donorApi.mydonations.MyDonationJson
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.donor.adapters.CampaignsAdapter
import com.example.graduationproject.donor.adapters.MyDonationAdapter
import com.example.graduationproject.network.RetrofitInstance
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.bottom_dialog_item_manual.view.*
import kotlinx.android.synthetic.main.campaign_item_in_donation_screen.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_my_donation.*
import kotlinx.android.synthetic.main.fragment_my_donation.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MyDonationFragment : Fragment(),
    MyDonationAdapter.onDonationItemClickListener {
    var progressDialog: ProgressDialog? = null
    lateinit var dialog: BottomSheetDialog
    lateinit var v: View

    companion object {
        fun newInstance(`val`: Int): MyDonationFragment {
            val fragment = MyDonationFragment()
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
        val root = inflater.inflate(R.layout.fragment_my_donation, container, false)

        root.all_no_donation.visibility = View.GONE
        root.rv_donations.visibility = View.GONE

        val sharedPref = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val user_id = sharedPref.getInt("user_id", 0)

        progressDialog = ProgressDialog(activity)
        GeneralChanges().showDialog(progressDialog!!, "جاري التحميل ....")
        getMyDonations(user_id)

        return root
    }

    private fun showDialog() {
        dialog = BottomSheetDialog(this.requireContext())
            v = layoutInflater.inflate(R.layout.bottom_dialog_item_manual, null)
            v.title.text = "تفاصيل التبرع"
            v.confirm.visibility = View.GONE

        dialog.setContentView(v)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

    }

    fun getMyDonations(user_id:Int) {

        val d = requireArguments().getInt("someInt", 0)
        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.getMyDonations(user_id,d)

        response.enqueue(object : Callback<MyDonationJson> {
            override fun onResponse(
                call: Call<MyDonationJson>,
                response: Response<MyDonationJson>
            ) {
                if (response.isSuccessful) {
                    var data = response.body()!!.data
                    if (data.isEmpty()) {
                        Log.e("get donation", "is empty")
                        all_no_donation.visibility = View.VISIBLE
                        rv_donations.visibility = View.GONE
                        GeneralChanges().hideDialog(progressDialog!!)

                    } else {
                        Log.e("get donation", "is not empty")
                        all_no_donation.visibility = View.GONE
                        rv_donations.visibility = View.VISIBLE

                            rv_donations.layoutManager = LinearLayoutManager(
                                activity,
                                RecyclerView.VERTICAL, false
                            )
                            rv_donations.setHasFixedSize(true)
                            val donationssAdapter =
                                MyDonationAdapter(
                                    activity,
                                    data,
                                    this@MyDonationFragment
                                )
                            rv_donations.adapter = donationssAdapter
                        donationssAdapter.notifyDataSetChanged()
                            GeneralChanges().hideDialog(progressDialog!!)

                    }

                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                    GeneralChanges().hideDialog(progressDialog!!)
                }

            }

            override fun onFailure(call: Call<MyDonationJson>, t: Throwable) {
                Log.e("failure", t.message!!)
                GeneralChanges().hideDialog(progressDialog!!)
            }
        })
    }

    override fun onItemClick(data: Data, position: Int) {
        showDialog()
        if (data.campaign_details != null){
            v.item.campaign_name.text = data.campaign_details.name
            Picasso.get().load(RetrofitInstance.IMAGE_URL+data.campaign_details.image).into(v.item.campaign_image)
        }else{
            v.item.campaign_name.text = data.charity_details.name
            Picasso.get().load(RetrofitInstance.IMAGE_URL+data.charity_details.image).into(v.item.campaign_image)
        }

        v.residential_district.text = data.donor_district
        v.city.text = data.donor_city
        v.location.text = data.donor_address
        v.phone_number.text = data.donor_phone.toString()

        v.r_time.text = data.date_time

        v.close.setOnClickListener {
            dialog.dismiss()
        }

    }

}