package com.example.mystory2.api.story.storyContent

data class StoryContentJson(
    val Success: Boolean,
    val code: Int,
    val feedback: List<Feedback>,
    val message: String
)