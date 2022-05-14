package com.example.graduationproject.api.donorApi.campaignAccordingToDonationType

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DonationType(
    val created_at: String,
    val deleted_at: String?,
    val id: Int,
    val image: String,
    val name: String,
    val updated_at: String
): Parcelable