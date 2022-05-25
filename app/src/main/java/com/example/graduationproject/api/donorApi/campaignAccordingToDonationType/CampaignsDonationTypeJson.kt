package com.example.graduationproject.api.donorApi.campaignAccordingToDonationType

import com.example.graduationproject.models.Campaigns

data class CampaignsDonationTypeJson(
    val `data`: ArrayList<Campaigns>,
    val message: String,
    val status: Boolean
)