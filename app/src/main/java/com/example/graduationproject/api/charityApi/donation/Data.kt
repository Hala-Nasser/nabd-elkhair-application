package com.example.graduationproject.api.charityApi.donation

import android.os.Parcelable
import com.example.graduationproject.models.Campaigns
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Data(
    val acceptance: Int,
    val campaign: Campaigns?,
    val campaign_id: Int,
    val charity_id: Int,
    val created_at: String,
    val deleted_at: String,
    val donation_type_id: Int,
    val date_time: String,
    val donor: @RawValue Donor?,
    val donor_address: String,
    val donor_city: String,
    val donor_district: String,
    val donor_id: Int,
    val donor_phone: String,
    val description: String,
    val id: Int,
    val milli: Long,
    val date: String,
    val received: Int,
    val updated_at: String
): Parcelable
