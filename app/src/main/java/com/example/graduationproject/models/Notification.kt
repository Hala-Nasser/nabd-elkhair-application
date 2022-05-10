package com.example.graduationproject.models

data class Notification(
    val created_at: String,
    val deleted_at: Any,
    val id: Int,
    val notification_content: String,
    val notification_image: String,
    val notification_title: String,
    val reciever_id: Int,
    val reciever_type: String,
    val updated_at: String
)