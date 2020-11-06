package com.mp.example.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mp.example.pages.articles.thenOptional

@Composable
fun Background(modifier: Modifier? = null, content: @Composable () -> Unit) {
    val lightGray = Color(230, 230, 230)
    Surface(
        color = lightGray,
        modifier = Modifier.fillMaxSize().thenOptional(modifier),
        content = content
    )
}