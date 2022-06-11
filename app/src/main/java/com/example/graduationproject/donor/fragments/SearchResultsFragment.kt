package com.example.graduationproject.donor.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.api.donorApi.charities.CharitiesJson
import com.example.graduationproject.api.donorApi.charities.Data
import com.example.graduationproject.donor.adapters.CharitiesAdapter
import com.example.graduationproject.models.Charity
import com.example.graduationproject.network.RetrofitInstance
import kotlinx.android.synthetic.main.activity_donor_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_search_results.*
import kotlinx.android.synthetic.main.fragment_search_results.view.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class SearchResultsFragment : Fragment(), CharitiesAdapter.onCharityItemClickListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_search_results, container, false)

        var data = arguments
        if (data != null) {
            var keyword = data.getString("keyword")
            getCharities(keyword!!)
        }

        root.back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return root
    }

    override fun onItemClick(data: Charity, position: Int) {
        val fragment = CharityDetailsFragment()
        val b = Bundle()
        b.putInt("charity_id", data.id)
        b.putString("charity_name", data.name)
        b.putString("charity_image", data.image)
        b.putString("charity_address", data.address)
        b.putString("charity_description", data.about)
        b.putString("charity_phone", data.phone)

        fragment.arguments = b

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, fragment).addToBackStack(null).commit()
        requireActivity().nav_bottom.visibility = View.GONE
    }

    fun getCharities(keyword: String) {

        val retrofitInstance =
            RetrofitInstance.create()
        val response = retrofitInstance.searchCharity(keyword)

        response.enqueue(object : retrofit2.Callback<CharitiesJson> {
            override fun onResponse(call: Call<CharitiesJson>, response: Response<CharitiesJson>) {
                if (response.isSuccessful) {
                    val data = response.body()!!.data

                    if (data.isEmpty()) {
                        no_results.visibility = View.VISIBLE
                        rv_charities.visibility = View.GONE
                    } else {
                        no_results.visibility = View.GONE
                        rv_charities.visibility = View.VISIBLE

                        rv_charities.layoutManager =
                            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                        rv_charities.setHasFixedSize(true)
                        val charitiesAdapter =
                            CharitiesAdapter(activity, data, this@SearchResultsFragment)
                        rv_charities.adapter = charitiesAdapter
                    }

                } else {
                    Log.e("error Body", response.errorBody()?.charStream()?.readText().toString())
                }

            }

            override fun onFailure(call: Call<CharitiesJson>, t: Throwable) {
                Log.e("failure", t.message!!)
            }
        })
    }

}