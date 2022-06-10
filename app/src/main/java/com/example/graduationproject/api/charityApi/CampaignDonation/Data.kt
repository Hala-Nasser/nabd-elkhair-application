package com.example.graduationproject.api.charityApi.CampaignDonation

import com.example.graduationproject.api.charityApi.donation.Data

data class Data(
    val charity_id: Int,
    val created_at: String,
    val deleted_at: String,
    val description: String,
    val donation: List<Data>,
    val donation_type_id: Int,
    val expiry_date: String,
    val expiry_time: String,
    val id: Int,
    val image: String,
    val name: String,
    val updated_at: String
)