package com.example.graduationproject.charity.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Complaint(var id:String,
                     var donorId:String?,
                     var charityId:String, var complaint_reason:String,
                     var complainerName:String, var complainerImg:String,
                     ): Parcelable

