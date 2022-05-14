package com.example.graduationproject.api.donorApi.paymentLinks

data class Data(
    val charity_id: Int,
    val created_at: String,
    val creditcard_link: String?,
    val id: Int,
    val paypal_link: String?,
    val updated_at: String,
    val visa_link: String?
)