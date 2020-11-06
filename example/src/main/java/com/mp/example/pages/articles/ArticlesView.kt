package com.mp.example.pages.articles

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mp.cubit.StateBuilder
import com.mp.cubit.StateViewOf
import com.mp.example.composables.*
import com.mp.example.pages.articles.models.ArticleEntryModel
import com.mp.example.pages.articles.ArticlesState.*

@kotlinx.coroutines.ExperimentalCoroutinesApi
class MainView(cubit: ArticlesCubit): StateViewOf<ArticlesCubit, ArticlesState>(cubit) {

    @Composable
    fun Compose() {
        Column(modifier = Modifier.fillMaxSize()) {
            StateBuilder { state ->
                val contentState = state as? Content
                val isLoading = contentState?.isRefreshing ?: false
                AppBar(
                    title = "Cubit Power",
                    onRefresh = cubit::refresh,
                    isLoading = isLoading
                )
            }
            StateBuilder { state ->
                when {
                    state !is Content -> LoadingContent()
                    state.isEmpty -> EmptyContent(state)
                    else -> FaqContent(state)
                }
            }
        }
    }

    @Composable
    private fun LoadingContent() {
        Background {
            MaxSizeBox(Center) {
                CircularProgressIndicator()
            }
        }
    }

    @Composable
    private fun EmptyContent(state: Content) {
        Background {
            Box(alignment = Center) {
                Text(
                    text = state.text ?: "",
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    @Composable
    private fun FaqContent(state: Content) {
        Box {
            Background {
                MaxSizeBox(TopCenter) {
                    FaqList(state)
                }
            }
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (fabs, hint) = createRefs()
                val fabsModifier = Modifier.constrainAs(fabs) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(hint.top)
                }.then(Modifier.fillMaxWidth())

                val hintModifier = Modifier.constrainAs(hint) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }

                ActionButtons(modifier = fabsModifier)
                AnimatedErrorHint(state.errorMessage, modifier = hintModifier)
            }
        }
    }

    @Composable
    private fun FaqList(state: Content) {
        LazyColumnFor(
            items = state.listData,
            modifier = Modifier.fillMaxSize()
        ) {
            FaqEntry(it)
        }
    }

    @Composable
    private fun FaqEntry(model: ArticleEntryModel) {

        val cardModifier = Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .then(Modifier.fillMaxWidth())
            .then(Modifier.clickable { cubit.onArticleEntryClicked(model) })

        val textPadding = Modifier.padding(16.dp)

        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(8.dp),
            modifier = cardModifier
        ) {
            Text(
                text = model.text,
                textAlign = TextAlign.Center,
                modifier = textPadding,
                color = Color(model.color)
            )
        }
    }

    @Composable
    private fun ActionButtons(modifier: Modifier? = null) {
        val endSpace = Modifier.padding(end = 16.dp).thenOptional(modifier)

        Column(
            modifier = endSpace,
        ) {
            FloatingActionButton(
                modifier = Modifier.align(Alignment.End),
                onClick = cubit::resetOnboarding,
                backgroundColor = Color.DarkGray,
            ) {
                Text(
                    text = "*",
                    color = Color.White
                )
            }
            Spacer(Modifier.height(16.dp))
            FloatingActionButton(
                modifier = Modifier.align(Alignment.End),
                onClick = cubit::showNextScreen,
                backgroundColor = Color.DarkGray,
            ) {
                Text(
                    text = "->",
                    color = Color.White
                )
            }
            Spacer(Modifier.height(16.dp))
            FloatingActionButton(
                modifier = Modifier.align(Alignment.End),
                onClick = cubit::generateContent,
                backgroundColor = Color.DarkGray
            ) {
                Text(
                    text = "+",
                    color = Color.White
                )
            }
            Spacer(Modifier.height(16.dp))
            FloatingActionButton(
                modifier = Modifier.align(Alignment.End),
                onClick = cubit::showErrorCountDown,
                backgroundColor = Color.DarkGray
            ) {
                Text(
                    text = "!",
                    color = Color.White
                )
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

fun Modifier.thenOptional(modifier: Modifier?): Modifier {
    if (modifier != null) {
        return then(modifier)
    }
    return this
}