package com.example.graduationproject.api.charityApi.fcm

import com.example.graduationproject.models.Charity
import kotlinx.android.parcel.RawValue

data class FCMJson(
    val `data`: Charity,
    val message: String,
    val status: Boolean
)