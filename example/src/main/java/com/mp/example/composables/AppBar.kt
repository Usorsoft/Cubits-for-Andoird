package com.jamitlabs.remoteui_sdk.composables

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AppBar(
    title: String,
    isLoading: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    onBackPressed: (() -> Unit)? = null
) {

    @Composable
    fun Leading() {
        onBackPressed?.let {
            val padding = Modifier.padding(start = 16.dp)
            Button(onClick = it, modifier = padding) {
                Text(text = "<-")
            }
        }
    }

    @Composable
    fun Trailing() {
        when {
            isLoading -> CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 2.dp,
                modifier = Modifier.size(24.dp)
            )
            onRefresh != null -> Button(onClick = onRefresh) {
                Text(text = "Refresh")
            }
        }
    }

    val surfaceModifier = Modifier.height(60.dp)
        .then(Modifier.fillMaxWidth())

    Surface(modifier = surfaceModifier, color = Color.DarkGray) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val textModifier = Modifier.padding(start = 16.dp)
            Leading()
            Text(
                text = title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                modifier = textModifier
            )
            Row {
                Trailing()
                Spacer(Modifier.width(16.dp))
            }
        }
    }
}