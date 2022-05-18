package com.example.graduationproject.api.charityApi.donation

data class DonationJson(
    val `data`: ArrayList<Data>,
    val message: String,
    val status: Boolean
)