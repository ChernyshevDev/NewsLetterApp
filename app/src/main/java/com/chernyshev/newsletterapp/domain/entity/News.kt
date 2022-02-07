package com.chernyshev.newsletterapp.domain.entity

import java.io.Serializable

data class NewsItem(
    val title: String = "",
    val description: String = "",
    val content: String = "",
    val url: String = "",
    val image: String = "",
    val publishedAt: String = "",
    val source: NewsSource? = null
) : Serializable

data class NewsSource(
    val name: String = "",
    val url: String = ""
) : Serializable