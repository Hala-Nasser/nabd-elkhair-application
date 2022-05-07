package com.example.graduationproject.api.donorApi.campaignAccordingToDonationType

import com.example.graduationproject.donor.models.Campaigns

data class CampaignsDonationTypeJson(
    val `data`: List<Campaigns>,
    val message: String,
    val status: Boolean
)