package com.example.graduationproject.api.donorApi.mydonations

data class MyDonationJson(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
)