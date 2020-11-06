package com.mp.example.pages.articles.models

import android.graphics.Color
import com.mp.example.repositories.Article

data class ArticleEntryModel(
    val title: String,
    val text: String,
    val imageUrl: String,
    val color: Int = Color.BLACK
)

fun Article.toDomainModel(color: Int = Color.BLACK) = ArticleEntryModel(title, text, imageUrl, color)