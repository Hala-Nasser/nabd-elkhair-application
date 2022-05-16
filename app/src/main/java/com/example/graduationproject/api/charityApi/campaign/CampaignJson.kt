package com.example.graduationproject.api.charityApi.campaign

import com.example.graduationproject.models.Campaigns
import com.example.graduationproject.models.CharityWithToken

data class CampaignJson(
    val `data`: Campaigns,
    val message: String,
    val status: Boolean
)