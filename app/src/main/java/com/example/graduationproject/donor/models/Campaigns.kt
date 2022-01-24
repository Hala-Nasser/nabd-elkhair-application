package com.example.graduationproject.donor.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Campaigns(var campaignImg:Int?,var campaignName:String,var campaignDate:String,var campaignDescription:String, var campaignDonationType: DonationType,var campaignCharity:Charity): Parcelable