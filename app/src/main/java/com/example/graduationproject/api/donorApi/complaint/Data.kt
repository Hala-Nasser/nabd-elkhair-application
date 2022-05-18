package com.example.graduationproject.api.donorApi.complaint

import com.example.graduationproject.api.charityApi.donation.Donor

data class Data(
    val complainer_id: Int,
    val complainer_type: String,
    val complaint_reason: ArrayList<String>?,
    val created_at: String,
    val defendant_id: String,
    val id: Int,
    val updated_at: String,
    val donor:Donor?
)