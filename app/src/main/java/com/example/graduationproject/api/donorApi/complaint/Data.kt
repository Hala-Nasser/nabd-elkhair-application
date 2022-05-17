package com.example.graduationproject.api.donorApi.complaint

data class Data(
    val complainer_id: Int,
    val complainer_type: String,
    val complaint_reason: ArrayList<String>,
    val created_at: String,
    val defendant_id: String,
    val id: Int,
    val updated_at: String
)