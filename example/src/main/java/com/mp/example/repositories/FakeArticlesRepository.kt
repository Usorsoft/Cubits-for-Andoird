package com.mp.example.repositories

import androidx.ui.tooling.preview.datasource.LoremIpsum
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.net.HttpURLConnection.HTTP_INTERNAL_ERROR
import java.net.HttpURLConnection.HTTP_OK
import kotlin.random.Random

class FakeArticlesRepository {

    suspend fun fetchArticles(): RepositoryResult<List<Article>> {
        runBlocking { delay(3000) }
        val simulateError = (Random.nextInt(until = 5) == 1)
        return when (simulateError) {
            true -> Failure(HTTP_INTERNAL_ERROR)
            false -> Success(HTTP_OK, buildArticles())
        }
    }

    private fun buildArticles() = listOf(
        Article(
            title = "Cats",
            text = LoremIpsum(10).values.joinToString(" "),
            imageUrl = "https://picsum.photos/200/300"
        ),
        Article(
            title = "Dogs",
            text = LoremIpsum(15).values.joinToString(" "),
            imageUrl = "https://picsum.photos/200/300"
        ),
        Article(
            title = "Kitten",
            text = LoremIpsum(25).values.joinToString(" "),
            imageUrl = "https://picsum.photos/200/300"
        ),
        Article(
            title = "Cubits",
            text = LoremIpsum(40).values.joinToString(" "),
            imageUrl = "https://picsum.photos/200/300"
        ),
        Article(
            title = "Lorem ipsum",
            text = LoremIpsum(100).values.joinToString(" "),
            imageUrl = "https://picsum.photos/200/300"
        )
    )
}

data class Article(
    val title: String,
    val text: String,
    val imageUrl: String
)