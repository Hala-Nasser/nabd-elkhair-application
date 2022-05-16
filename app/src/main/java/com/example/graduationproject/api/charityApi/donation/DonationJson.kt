package com.example.graduationproject.api.charityApi.donation

data class DonationJson(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
)