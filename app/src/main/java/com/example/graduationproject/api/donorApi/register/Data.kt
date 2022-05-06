package com.example.graduationproject.api.donorApi.register


data class Data(
    val activation_status: String,
    val created_at: String,
    val email: String,
    val id: Int,
    val image: String,
    val location: String,
    val name: String,
    val phone: String,
    val updated_at: String
)