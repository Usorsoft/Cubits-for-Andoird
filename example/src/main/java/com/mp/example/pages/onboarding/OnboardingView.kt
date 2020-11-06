package com.jamitlabs.remoteui_sdk.onboarding

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jamitlabs.remoteui_sdk.composables.AppBar
import com.jamitlabs.remoteui_sdk.composables.Background
import com.mp.cubit.StateViewOf
import com.mp.example.R

class OnboardingView(cubit: OnboardingCubit) :
    StateViewOf<OnboardingCubit, OnboardingCubit.State>(cubit) {

    @Composable
    fun Compose() {
        Column {
            AppBar(title = stringResource(R.string.onboarding_title))
//            Background {
//                Box(modifier = Modifier.fillMaxSize(), alignment = Alignment.Center) {
//                    Text(text = stringResource(R.string.onboarding_body_message))
//                }
//                Box(modifier = Modifier.fillMaxSize(), alignment = Alignment.BottomEnd) {
//                    FloatingActionButton(
//                        onClick = cubit::showArticles,
//                        modifier = Modifier.padding(16.dp)
//                    ) {
//                        Text(text = ">")
//                    }
//                }
//            }
        }
    }
}