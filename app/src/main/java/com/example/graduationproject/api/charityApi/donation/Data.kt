package com.example.graduationproject.api.charityApi.donation

import com.example.graduationproject.models.Campaigns

data class Data(
    val acceptance: Int,
    val campaign: Campaigns?,
    val campaign_id: Int,
    val charity_id: Int,
    val created_at: String,
    val deleted_at: Any,
    val donation_amount: String,
    val donation_type_id: Int,
    val donation_way: String,
    val donor: Donor?,
    val donor_address: String,
    val donor_city: String,
    val donor_district: String,
    val donor_id: Int,
    val donor_phone: String,
    val id: Int,
    val payment_link: String,
    val received: Int,
    val updated_at: String
)