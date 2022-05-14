package com.example.graduationproject.charity.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentLink(var id:Int,
                       var charityId:Int,
                       var name:String?, var image:Int?,
                       var link:String?): Parcelable

