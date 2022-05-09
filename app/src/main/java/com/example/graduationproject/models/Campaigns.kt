package com.example.graduationproject.models

import android.os.Parcelable
import com.example.graduationproject.api.donorApi.campaignAccordingToDonationType.DonationType
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Campaigns(
    var charity: @RawValue Charity,
    var charity_id: Int,
    var created_at: String,
    var deleted_at: String?,
    var description: String,
    val donation_type: DonationType,
    var donation_type_id: Int,
    var expiry_date: String,
    var expiry_time: String,
    var id: Int,
    var image: String,
    var name: String,
    var updated_at: String
): Parcelable