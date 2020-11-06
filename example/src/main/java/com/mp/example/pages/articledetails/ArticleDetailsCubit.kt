package com.mp.example.pages.articledetails

import com.mp.cubit.Cubit
import com.mp.cubit.foundation.NavigateBack
import com.mp.example.pages.articles.models.ArticleEntryModel


class ArticleDetailsCubit(private val model: ArticleEntryModel) : Cubit<ArticleDetailsState>() {

    override fun initState() = ArticleDetailsState.Initial

    fun init() {
        val contentState = ArticleDetailsState.Content(model)
        emitState(contentState)
    }

    fun onBackPressed() {
        emitAction(NavigateBack)
    }

    override fun onDispose() {
        //TODO: Dispose your stuff
    }
}
