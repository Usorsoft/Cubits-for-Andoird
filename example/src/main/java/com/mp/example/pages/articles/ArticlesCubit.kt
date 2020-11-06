package com.jamitlabs.remoteui_sdk.main

import android.graphics.Color
import com.jamitlabs.remoteui_sdk.NavigateNext
import com.jamitlabs.remoteui_sdk.repositories.PreferencesRepository
import com.mp.cubit.Cubit
import com.mp.cubit.foundation.repeatReversed
import com.mp.example.pages.articles.ShowArticleDetails
import com.mp.example.pages.articles.models.ArticleEntryModel
import com.mp.example.pages.articles.models.toDomainModel
import com.mp.example.repositories.Article
import com.mp.example.repositories.FakeArticlesRepository
import com.mp.example.repositories.Success
import kotlinx.coroutines.delay

/**
 * Serves as the business logic component for the [ArticlesActivity].
 * <p>
 * Created by Michael Pankraz on 12.07.20.
 * <p>
 * Copyright EnBW AG
 */
@kotlinx.coroutines.ExperimentalCoroutinesApi
class ArticlesCubit(
    private val articlesRepo: FakeArticlesRepository,
    private val preferences: PreferencesRepository
): Cubit<ArticlesState>() {

    private var colorIndex = 0
    private val colors = listOf(Color.BLUE, Color.RED, Color.MAGENTA)
    override fun initState() = ArticlesState.Initial

    /**
     * This loads the data from the Dynamic web tool and emits them.
     */
    fun loadData() = async(LOAD_DATA) {
        if (state !is ArticlesState.Content) {
            emitState(ArticlesState.InitialLoading)
            val result = articlesRepo.fetchArticles()
            val articles = result as? Success ?: return@async emitState(ArticlesState.InitialFail())
            val mainState = parseContent(articles.value)
            emitState(mainState)
        }
    }

    /**
     * This loads the data from the Dynamic web tool and keeps the current content while loading.
     */
    fun refresh() = async(REFRESH) {
        val immutableState = state
        if (immutableState is ArticlesState.Content) {
            emitState(immutableState.copy(isRefreshing = true))
            val result = articlesRepo.fetchArticles()
            val articles = result as? Success ?: return@async emitState(ArticlesState.InitialFail())
            val mainState = parseContent(articles.value)
            emitState(mainState)
        }
    }

    /**
     * This generates some list entries and emits the spread of a period of time.
     */
    fun generateContent() = async(GEN_CONTENT, cancelExistingJob = true) {
        repeat(5) {
            val contentState = state as? ArticlesState.Content ?: return@async
            val generatedItem = generateRandomArticle(nextColor())
            val items = listOf(generatedItem) + contentState.listData
            val nextState = contentState.copy(listData = items)
            emitState(nextState)
            delay(1000)
        }
    }

    /**
     * This emits error messages spread over a period of time.
     */
    fun showErrorCountDown() = async {
        repeatReversed(5) {
            val contentState = state as? ArticlesState.Content ?: return@async
            emitState(contentState.copy(errorMessage = "Error... [$it]"))
            delay(1000)
        }

        val contentState = state as? ArticlesState.Content ?: return@async
        emitState(contentState.copy(errorMessage = null))
    }

    fun resetOnboarding() {
        preferences.showOnboarding = true
    }

    fun showNextScreen() {
        emitAction(NavigateNext)
    }

    fun onArticleEntryClicked(model: ArticleEntryModel) {
        emitAction(ShowArticleDetails(model))
    }

    /**
     * This returns the next color from a predefined list.
     */
    private fun nextColor() = colors[colorIndex++ % colors.size]

    private fun generateRandomArticle(color: Int) = ArticleEntryModel(
        title = "Generated content",
        text = "Lorem ipsum",
        imageUrl = "",
        color = color
    )

    /**
     * This parses to given [Record.Collection] into a [MainStateContent].
     */
    private fun parseContent(articles: List<Article>): ArticlesState.Content {
        return ArticlesState.Content(
            isRefreshing = false,
            text = "No articles found",
            isEmpty = articles.isEmpty(),
            listData = articles.map { it.toDomainModel() }
        )
    }

    override fun onDispose() {}

    companion object {
        private const val LOAD_DATA = "load_data"
        private const val REFRESH = "refresh"
        private const val GEN_CONTENT = "generate_content"
    }
}
















































//abstract class MainState {
//    abstract val isRefreshing: Boolean
//    abstract val textVisibility: Int
//    abstract val refreshButtonVisibility: Int
//    abstract val faqVisibility: Int
//    abstract val text: String?
//    abstract val faqEntries: List<String>
//}
//
//object MainStateInitial: MainState() {
//    override val isRefreshing = false
//    override val textVisibility = GONE
//    override val refreshButtonVisibility = GONE
//    override val faqVisibility = GONE
//    override val text: String? = null
//    override val faqEntries = emptyList<String>()
//}
//
//object MainStateLoading: MainState() {
//    override val isRefreshing = true
//    override val textVisibility = GONE
//    override val refreshButtonVisibility = GONE
//    override val faqVisibility = GONE
//    override val text: String? = null
//    override val faqEntries = emptyList<String>()
//}
//
//class MainStateLoadingFailed: MainState() {
//    override val isRefreshing = false
//    override val textVisibility = VISIBLE
//    override val refreshButtonVisibility = VISIBLE
//    override val faqVisibility = GONE
//    override val text = "Sorry, something went wrong :("
//    override val faqEntries = emptyList<String>()
//}
//
//data class MainStateShowContent(
//    private val message: String?,
//    private val isTextVisible: Boolean,
//    private val showFaq: Boolean,
//    private val entries: List<String>
//): MainState() {
//    override val isRefreshing = false
//    override val textVisibility = if (isTextVisible) VISIBLE else GONE
//    override val refreshButtonVisibility = GONE
//    override val faqVisibility = if (showFaq) VISIBLE else GONE
//    override val text = message
//    override val faqEntries = entries
//}