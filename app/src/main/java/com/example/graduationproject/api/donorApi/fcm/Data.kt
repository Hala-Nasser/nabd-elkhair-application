package com.example.graduationproject.api.donorApi.fcm

data class Data(
    val activation_status: Int,
    val created_at: String,
    val deleted_at: Any,
    val email: String,
    val email_verified_at: Any,
    val fcm_token: String,
    val id: Int,
    val image: String,
    val location: String,
    val name: String,
    val phone: String,
    val updated_at: String
)