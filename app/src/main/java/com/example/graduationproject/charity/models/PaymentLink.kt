package com.example.graduationproject.charity.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentLink(var id:String,
                       var charityId:String,
                       var paypal_link:String?, var visa_link:String?,
                       var creditCard_link:String?): Parcelable

