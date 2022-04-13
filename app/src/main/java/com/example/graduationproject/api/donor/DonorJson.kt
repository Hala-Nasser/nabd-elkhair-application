package com.example.graduationproject.api.donor

data class DonorJson(
    val activation_status: String,
    val created_at: String,
    val email: String,
    val id: Int,
    val image: Image,
    val location: String,
    val name: String,
    val phone: String,
    val updated_at: String
)