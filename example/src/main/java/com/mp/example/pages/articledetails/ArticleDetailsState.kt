package com.mp.example.pages.articledetails

import com.mp.example.pages.articles.models.ArticleEntryModel


sealed class ArticleDetailsState {
  object Initial: ArticleDetailsState()
  object Loading: ArticleDetailsState()
  data class Content(val article: ArticleEntryModel): ArticleDetailsState()
}
