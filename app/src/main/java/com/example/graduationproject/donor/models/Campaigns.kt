package com.example.graduationproject.donor.models

import android.os.Parcelable
import com.example.graduationproject.charity.models.Donation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Campaigns(
                     var campaignId: String?,
                     var campaignImg:Int?,
                     var campaignName:String,
                     var campaignDate:String,
                     var campaignTime:String?=null,
                     var campaignDescription:String,
                     var campaignDonationType: DonationType,
                     var campaignCharity:Charity,
                     var donation: List<Donation>?=null
                     ): Parcelable
