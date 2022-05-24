package com.example.graduationproject.api.donorApi.mydonations

data class CharityDetails(
    val about: String,
    val activation_status: Int,
    val address: String,
    val created_at: String,
    val deleted_at: Any,
    val donationTypes: List<String>,
    val email: String,
    val email_verified_at: Any,
    val fcm_token: Any,
    val first_activiation: Int,
    val id: Int,
    val image: String,
    val name: String,
    val notification_status: Int,
    val open_time: String,
    val phone: Int,
    val updated_at: String
)