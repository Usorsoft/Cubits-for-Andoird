package com.mp.example.pages.articles

import com.mp.cubit.foundation.NavigationAction
import com.mp.example.pages.articles.models.ArticleEntryModel

data class ShowArticleDetails(val model: ArticleEntryModel): NavigationAction()