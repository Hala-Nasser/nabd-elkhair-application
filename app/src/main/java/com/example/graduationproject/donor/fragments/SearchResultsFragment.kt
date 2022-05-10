package com.example.graduationproject.donor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationproject.R
import com.example.graduationproject.api.donorApi.charities.Data
import com.example.graduationproject.donor.adapters.CharitiesAdapter
import com.example.graduationproject.models.Charity

class SearchResultsFragment : Fragment(), CharitiesAdapter.onCharityItemClickListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_search_results, container, false)

//        var charitiesList = mutableListOf<Charity>()
//        charitiesList.add(Charity(R.drawable.charity_image,"جمعية الاحسان الخيرية","فلسطين, غزة"))
//        charitiesList.add(Charity(R.drawable.charity_image,"جمعية الاحسان الخيرية","فلسطين, غزة"))
//        charitiesList.add(Charity(R.drawable.charity_image,"جمعية الاحسان الخيرية","فلسطين, غزة"))
//        charitiesList.add(Charity(R.drawable.charity_image,"جمعية الاحسان الخيرية","فلسطين, غزة"))
//
//
//        root.rv_charities.layoutManager = LinearLayoutManager(activity,
//            RecyclerView.VERTICAL,false)
//        root.rv_charities.setHasFixedSize(true)
//        val charitiesAdapter =
//            CharitiesAdapter(this.activity, charitiesList, this)
//        root.rv_charities.adapter = charitiesAdapter
        return root
    }

    override fun onItemClick(data: Charity, position: Int) {
        TODO("Not yet implemented")
    }

}