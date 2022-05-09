package com.example.graduationproject.api.charityApi.login

data class Data(
    val about: String,
    val activation_status: Int,
    val address: String,
    val created_at: String,
    val deleted_at: Any,
    val donationTypes: List<String>,
    val email: String,
    val email_verified_at: Any,
    val fcm_token: Any,
    val first_activiation: Any,
    val id: Int,
    val image: String,
    val name: String,
    val open_time: String,
    val phone: Int,
    val token: String,
    val updated_at: String
)