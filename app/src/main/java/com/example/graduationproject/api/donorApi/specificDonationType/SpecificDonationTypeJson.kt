package com.example.graduationproject.api.donorApi.specificDonationType

import com.example.graduationproject.api.donorApi.campaignAccordingToDonationType.DonationType

data class SpecificDonationTypeJson(
    val `data`: DonationType,
    val message: String,
    val status: Boolean
)