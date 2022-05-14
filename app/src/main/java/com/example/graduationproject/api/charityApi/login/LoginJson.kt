package com.example.graduationproject.api.charityApi.login

import com.example.graduationproject.models.CharityWithToken

data class LoginJson(
    val `data`: CharityWithToken,
    val message: String,
    val status: Boolean
)