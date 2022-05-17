package com.example.graduationproject.api.donorApi.addDonation

data class Data(
    val campaign_id: String,
    val charity_id: String,
    val created_at: String,
    val date_time: String,
    val donation_type_id: String,
    val donor_address: String,
    val donor_city: String,
    val donor_district: String,
    val donor_id: Int,
    val donor_phone: String,
    val id: Int,
    val updated_at: String
)