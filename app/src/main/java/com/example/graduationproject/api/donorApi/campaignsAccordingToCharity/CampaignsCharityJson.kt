package com.example.graduationproject.api.donorApi.campaignsAccordingToCharity

import com.example.graduationproject.models.Campaigns

data class CampaignsCharityJson(
    val `data`: List<Campaigns>,
    val message: String,
    val status: Boolean
)