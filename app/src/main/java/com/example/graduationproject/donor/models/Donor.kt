package com.example.graduationproject.donor.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Donor( var name:String?,
                 var email:String?, var phone:String?,
                 var location:String?, var image:String?,
                 var activation_status:Int?,var password:String?,var confirm_password:String?):
    Parcelable
