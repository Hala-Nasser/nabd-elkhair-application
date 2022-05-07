package com.example.graduationproject.api.donorApi.campaignAccordingToDonationType

data class Charity(
    val about: String,
    val activation_status: Int,
    val address: String,
    val created_at: String,
    val deleted_at: Any,
    val email: String,
    val email_verified_at: Any,
    val first_activiation: Int,
    val id: Int,
    val image: String,
    val name: String,
    val open_time: String,
    val phone: Int,
    val updated_at: String
)