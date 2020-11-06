package com.mp.example.pages.articles

import android.graphics.Color
import com.mp.example.pages.articles.models.ArticleEntryModel

/**
 * Represents the states of the main activity.
 * <p>
 * Created by Michael Pankraz on 04.10.20.
 * <p>
 * Copyright EnBW AG
 */
abstract class ArticlesState {

    object Initial: ArticlesState()

    object InitialLoading: ArticlesState()

    class InitialFail(val errorMessage: String = "Uppps..."): ArticlesState()

    data class Content(
        val isRefreshing: Boolean,
        val isEmpty: Boolean,
        val text: String? = null,
        val listData: List<ArticleEntryModel> = emptyList(),
        val listTextColor: Int = Color.BLACK,
        val errorMessage: String? = null
    ): ArticlesState()
}









