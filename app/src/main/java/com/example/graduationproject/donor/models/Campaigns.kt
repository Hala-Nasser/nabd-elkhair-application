package com.example.graduationproject.donor.models

import android.os.Parcelable
import com.example.graduationproject.charity.models.Donation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Campaigns(
    var charity_id: Int,
    var created_at: String,
    var deleted_at: String?,
    var description: String,
    var donation_type_id: Int,
    var expiry_date: String,
    var expiry_time: String,
    var id: Int,
    var image: String,
    var name: String,
    var updated_at: String
    ): Parcelable
