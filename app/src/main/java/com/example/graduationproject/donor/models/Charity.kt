package com.example.graduationproject.donor.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Charity(var charityImg:Int?,var charityName:String,var charityLocation:String):
    Parcelable
