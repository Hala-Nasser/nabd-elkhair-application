package com.example.mystory2.api.story

data class Story(
    val author_id: Int,
    val author_name: String,
    val comecbook: String,
    val comments_count: Int,
    val likes_count: Int,
    val painter_id: Int,
    val painter_name: String,
    val story_id: Int,
    val story_img: String,
    val story_link: String,
    val story_link_mobile: String,
    val story_title: String,
    val story_views: Int
)