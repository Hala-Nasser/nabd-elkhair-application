package com.example.graduationproject.charity.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Notification(var notificationImg:Int?,
                        var notificationTitle:String,
                        var notificationTime:String):
    Parcelable


