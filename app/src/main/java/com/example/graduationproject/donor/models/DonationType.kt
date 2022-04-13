package com.example.graduationproject.donor.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DonationType(var photo:Int?,var name:String): Parcelable
