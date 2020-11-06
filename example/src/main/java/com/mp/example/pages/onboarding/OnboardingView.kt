package com.mp.example.pages.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mp.cubit.StateViewOf
import com.mp.example.R
import com.mp.example.composables.AppBar

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