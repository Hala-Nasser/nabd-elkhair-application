package com.example.graduationproject.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Charity(
    val about: String,
    val activation_status: Int,
    val address: String,
    val created_at: String,
    val deleted_at: String?,
    val donationTypes: List<Int>,
    val email: String,
    val email_verified_at: String?,
    val fcm_token: String,
    val first_activiation: Int?,
    val id: Int,
    val image: String,
    val name: String,
    val open_time: String,
    val phone: String,
    val notification_status:Int,
    val updated_at: String
): Parcelable