package com.mp.example.pages.articledetails

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jamitlabs.remoteui_sdk.composables.AppBar
import com.jamitlabs.remoteui_sdk.composables.Background
import com.mp.cubit.StateBuilderOf
import com.mp.cubit.StateViewOf
import com.mp.example.pages.articledetails.ArticleDetailsState.Content
import com.mp.example.pages.articles.models.ArticleEntryModel


class ArticleDetailsView(cubit: ArticleDetailsCubit): StateViewOf<ArticleDetailsCubit, ArticleDetailsState>(cubit) {

    @Composable
    fun Compose() {
        StateBuilderOf<Content> {
            Background {
                Column {
                    AppBar(title = it.article.title, onBackPressed = cubit::onBackPressed)
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(it.article)
                }
            }
        }
    }

    @Composable
    fun Card(model: ArticleEntryModel) {
        val cardModifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .then(Modifier.fillMaxWidth())

        val textPadding = Modifier.padding(start = 16.dp, end = 16.dp)

        androidx.compose.material.Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(8.dp),
            modifier = cardModifier
        ) {
            Column {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = model.title,
                    textAlign = TextAlign.Center,
                    modifier = textPadding,
                    color = Color(model.color)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = model.text,
                    textAlign = TextAlign.Left,
                    modifier = textPadding,
                    color = Color.LightGray
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
