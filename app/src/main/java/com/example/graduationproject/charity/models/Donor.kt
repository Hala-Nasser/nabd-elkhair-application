package com.example.graduationproject.charity.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Donor(var donorImg:Int?, var donorName:String, var donorLocation:String):
    Parcelable

