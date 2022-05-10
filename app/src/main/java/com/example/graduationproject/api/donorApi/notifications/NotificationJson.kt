package com.example.graduationproject.api.donorApi.notifications

import com.example.graduationproject.models.Notification

data class NotificationJson(
    val `data`: List<Notification>,
    val message: String,
    val status: Boolean
)