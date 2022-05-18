package com.example.graduationproject.api.donorApi.complaint

data class ComplaintJson(
    val `data`: List<Data>?,
    val message: String,
    val status: Boolean
)