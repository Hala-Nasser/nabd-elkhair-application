package com.example.mystory2.api.story

data class StoryJson(
    val Success: Boolean,
    val code: Int,
    val message: String,
    val stories: List<Story>
)