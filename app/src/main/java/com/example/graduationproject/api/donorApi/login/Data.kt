package com.example.graduationproject.api.donorApi.login

data class Data(
    val notification_status: Int,
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
    val phone: Int,
    val token: String,
    val updated_at: String
)