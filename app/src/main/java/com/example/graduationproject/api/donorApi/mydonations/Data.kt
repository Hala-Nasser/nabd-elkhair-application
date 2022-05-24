package com.example.graduationproject.api.donorApi.mydonations

data class Data(
    val acceptance: Int,
    val campaign_details: CampaignDetails?,
    val campaign_id: Int?,
    val charity_details: CharityDetails,
    val charity_id: Int,
    val created_at: String,
    val date_time: String,
    val deleted_at: String?,
    val donation_type_id: Int,
    val donor_address: String,
    val donor_city: String,
    val donor_district: String,
    val donor_id: Int,
    val donor_phone: Int,
    val id: Int,
    val received: Int,
    val updated_at: String
)