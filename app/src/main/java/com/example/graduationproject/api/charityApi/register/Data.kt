package com.example.graduationproject.api.charityApi.register

data class Data(
    val about: String,
    val activation_status: String,
    val address: String,
    val created_at: String,
    val donationTypes: ArrayList<String>,
    val email: String,
    val id: Int,
    val image: String,
    val name: String,
    val open_time: String,
    val phone: String,
    val updated_at: String
)