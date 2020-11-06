package com.mp.example.composables

import androidx.compose.animation.DpPropKey
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TransitionState
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.core.tween
import androidx.compose.animation.transition
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mp.example.pages.articles.thenOptional
import com.mp.example.composables.ErrorHintAnimationState.*

val height = DpPropKey()

enum class ErrorHintAnimationState {
    HIDDEN, SHOW
}

@Composable
fun ErrorHint(message: String, transitionState: TransitionState? = null, modifier: Modifier? = null) {
    val height = transitionState?.get(height) ?: 60.dp
    val surfaceModifier = Modifier.height(height).then(Modifier.fillMaxWidth()).thenOptional(modifier)

    Surface(
        color = Color.Red,
        modifier = surfaceModifier
    ) {
        Box(alignment = Alignment.Center) {
            Text(
                text = message,
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun AnimatedErrorHint(message: String? = null, modifier: Modifier? = null) {
    val state = remember {
        mutableStateOf(HIDDEN)
    }

    val transDef = transitionDefinition<ErrorHintAnimationState> {
        state(HIDDEN) {
            this[height] = 0.dp
        }

        state(SHOW) {
            this[height] = 80.dp
        }

        transition(HIDDEN to SHOW) {
            height using tween(durationMillis = 500)
        }

        transition(SHOW to HIDDEN) {
            height using tween(durationMillis = 500, easing = FastOutSlowInEasing)
        }
    }

    val transitionState = transition(
        definition = transDef,
        initState = state.value,
        toState = if (message == null) HIDDEN else SHOW,
    ) { state.value = it }

    ErrorHint(message = message ?: "", transitionState, modifier)
}
