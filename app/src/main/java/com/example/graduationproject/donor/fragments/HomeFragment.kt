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
import com.example.graduationproject.api.category.CategoryJson
import com.example.graduationproject.api.category.Data
import com.example.graduationproject.donor.adapters.DonationTypeAdapter
import com.example.graduationproject.donor.models.DonationType
import com.example.graduationproject.network.ApiRequests
import com.example.graduationproject.network.RetrofitInstance
import com.example.mystory2.api.story.StoryJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

class HomeFragment : Fragment() {

    lateinit var rv_donation_type: RecyclerView
    var categoriesList = listOf<DonationType>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        rv_donation_type = root.findViewById(R.id.rv_donation_type)
       // getCategories()
        return root
    }


    fun getCategories(){
        Log.e("TAG", "stories")

        val response = RetrofitInstance.create().getCategories()

        response.enqueue(object : Callback<CategoryJson> {
            override fun onResponse(call: Call<CategoryJson>, response: Response<CategoryJson>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.e("TAG", "Stories success")
//                    categoriesList = data!!.data
                    rv_donation_type.layoutManager = LinearLayoutManager(activity,RecyclerView.HORIZONTAL,false)
                    rv_donation_type.setHasFixedSize(true)
                    val storyAdapter =
                        DonationTypeAdapter(activity, categoriesList,"DonorHome")
                    rv_donation_type.adapter = storyAdapter
                } else {
                    Log.e("TAG", "Stories unsuccess")
                }

            }

            override fun onFailure(call: Call<CategoryJson>, t: Throwable) {
                Log.e("TAG", t.message!!)
            }
        })
    }

}