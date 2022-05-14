package com.example.graduationproject.api.donorApi.charities

import com.example.graduationproject.models.Charity

data class CharitiesJson(
    val `data`: List<Charity>,
    val message: String,
    val status: Boolean
)