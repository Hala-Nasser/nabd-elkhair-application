package com.example.graduationproject.api.donorApi.donationType

import com.example.graduationproject.api.donorApi.campaignAccordingToDonationType.DonationType

data class DonationTypeJson(
    val `data`: List<DonationType>,
    val message: String,
    val status: Boolean
)