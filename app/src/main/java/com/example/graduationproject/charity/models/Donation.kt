package com.example.graduationproject.charity.models

import android.os.Parcel
import android.os.Parcelable
import com.example.graduationproject.donor.models.Campaigns
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Donation(var donors:Donor?=null,
                    var campaignId:String?,
                    var donorPhoneNumber:String, var donorPrefecture:String,
                    var donorCity:String, var donorAddress:String,
                    var donationAmount:String): Parcelable

