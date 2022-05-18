package com.example.graduationproject.charity.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.example.graduationproject.R
import com.example.graduationproject.api.donorApi.complaint.ComplaintJson
import com.example.graduationproject.api.donorApi.updateProfile.UpdateProfileJson
import com.example.graduationproject.classes.FileUtil
import com.example.graduationproject.classes.GeneralChanges
import com.example.graduationproject.classes.Validation
import com.example.graduationproject.donor.fragments.ProfileFragment
import com.example.graduationproject.network.RetrofitInstance
import kotlinx.android.synthetic.main.activity_charity_main.*
import kotlinx.android.synthetic.main.activity_donor_main.*
import kotlinx.android.synthetic.main.fragment_add_complaint.view.*
import kotlinx.android.synthetic.main.fragment_add_complaint.view.back
import kotlinx.android.synthetic.main.fragment_add_complaint.view.expandable_layout_1
import kotlinx.android.synthetic.main.fragment_add_complaint.view.image1
import kotlinx.android.synthetic.main.fragment_add_complaint.view.indicator1
import kotlinx.android.synthetic.main.fragment_add_complaint.view.title1
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import net.cachapa.expandablelayout.ExpandableLayout
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AddComplaintFragment : Fragment() {

    var token = ""
    var cToken = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_add_complaint, container, false)

        val sharedPref= requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        token = sharedPref.getString("user_token", "")!!
        cToken = sharedPref.getString("charity_token", "")!!

        val cal = Calendar.getInstance()
        val month_date = SimpleDateFormat("MMMM")
        val month_name = month_date.format(cal.time)
        val year = cal.get(Calendar.YEAR)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        root.monthAndYear.text = "$month_name $year"
        root.day.text = day.toString()

        var complaint_reasons : Array<String> = emptyArray()
        var reasons = ArrayList<String>()

        val b = arguments
        val from = b!!.getString("from")
        if (from=="Charity"){
            requireActivity().charity_nav_bottom.visibility=View.GONE
            complaint_reasons = resources.getStringArray(R.array.charity_complaint_reason_array)
        }else{
            //requireActivity().nav_bottom.visibility=View.GONE
            complaint_reasons = resources.getStringArray(R.array.donor_complaint_reason_array)
        }

        for (reason in complaint_reasons.indices){
            val cb = CheckBox(activity)
            cb.id = reason
            cb.text = complaint_reasons[reason]
            cb.isChecked = false
            root.cb_layout.addView(cb)

            cb.setOnCheckedChangeListener { _, _ ->
                if (cb.isChecked){
                    Log.e("cb", "true")
                    reasons.add(cb.text.toString())
                    Log.e("reasons check", reasons.toString())
                }else{
                    Log.e("cb", "false")
                    if (reasons.contains(cb.text.toString())){
                        reasons.remove(cb.text.toString())
                        Log.e("reasons uncheck", reasons.toString())
                    }
                }
                Log.e("reasons after", reasons.toString())

            }
        }

        root.send.setOnClickListener {
            val other = root.other_reason.text.toString()
            if (other.isNotEmpty()){
                reasons.add(other)
            }
            val b = arguments
            if (b != null) {
                var charity_id = b.getInt("charity_id")
                var donor_id = b.getInt("donor_id")
                if (from=="Charity")
                    charityAddComplaint(donor_id,reasons)
                    else
                addComplaint(charity_id,reasons)
            }


        }
        
        

        root.complaint_type_layout.isActivated = true
        root.complaint_type_layout.setOnClickListener {
            if (root.complaint_type_layout.isActivated){
                root.expandable_layout_1.isExpanded = !root.expandable_layout_1.isExpanded
            }
        }

        root.expandable_layout_1.setOnExpansionUpdateListener { _, _ ->
            layoutStyle(root.indicator1, root.expandable_layout_1)
        }

        root.back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return root
    }

    fun layoutStyle(indicator: ImageView, layout: ExpandableLayout){

        if (layout.isExpanded){
            indicator.setImageResource(R.drawable.ic_arrow_down)
        }else{
            indicator.setImageResource(R.drawable.ic_arrow_up)
        }

    }

    fun addComplaint(charity_id:Int, reasons:ArrayList<String>) {

        var body = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("defendant_id", charity_id.toString())
                .addFormDataPart("complainer_type", "donor")
        for (i in reasons){
            body.addFormDataPart("complaint_reason[]", i)
        }

        var requestBody = body.build()


        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.addComplaint(requestBody, "Bearer $token")

        response.enqueue(object : Callback<ComplaintJson> {
            override fun onResponse(call: Call<ComplaintJson>, response: Response<ComplaintJson>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data!!.status) {

                        requireActivity().onBackPressed()

                    }else{
                        Validation().showSnackBar(parent_layout, data.message)
                    }

                } else {
                    Log.e("errorBody", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<ComplaintJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
    }

    fun charityAddComplaint(donor_id:Int, reasons:ArrayList<String>) {

        var body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("defendant_id", donor_id.toString())
            .addFormDataPart("complainer_type", "charity")
        for (i in reasons){
            body.addFormDataPart("complaint_reason[]", i)
        }

        var requestBody = body.build()


        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.charityAddComplaint(requestBody, "Bearer $cToken")

        response.enqueue(object : Callback<ComplaintJson> {
            override fun onResponse(call: Call<ComplaintJson>, response: Response<ComplaintJson>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data!!.status) {

                        requireActivity().onBackPressed()

                    }else{
                        Validation().showSnackBar(parent_layout, data.message)
                    }

                } else {
                    Log.e("errorBody", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<ComplaintJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
    }
}