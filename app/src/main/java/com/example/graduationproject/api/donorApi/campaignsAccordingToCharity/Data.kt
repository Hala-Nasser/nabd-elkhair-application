package com.example.graduationproject.api.donorApi.campaignsAccordingToCharity


data class Data(
    val charity_id: Int,
    val created_at: String,
    val deleted_at: Any,
    val description: String,
    val donation_type_id: Int,
    val expiry_date: String,
    val expiry_time: String,
    val id: Int,
    val image: String,
    val name: String,
    val updated_at: String
)