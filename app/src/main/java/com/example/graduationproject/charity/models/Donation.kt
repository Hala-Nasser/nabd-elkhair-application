package com.example.graduationproject.charity.models

import android.os.Parcelable
import com.example.graduationproject.donor.models.Donor
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Donation(var donors: Donor?=null,
                    var campaignId:String?,
                    var donorPhoneNumber:String, var donorDistrict:String,
                    var donorCity:String, var donorAddress:String,
                    var donationAmount:String): Parcelable

