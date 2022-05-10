package com.example.graduationproject.api.donorApi.updateProfile

data class Data(
    val activation_status: Int,
    val created_at: String,
    val deleted_at: String?,
    val email: String,
    val email_verified_at: String?,
    val fcm_token: String,
    val id: Int,
    val image: String,
    val location: String,
    val name: String,
    val phone: String,
    val updated_at: String
)