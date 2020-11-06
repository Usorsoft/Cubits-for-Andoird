package com.mp.example.start

sealed class StartAction {
    object ShowOnboarding: StartAction()
    object ShowArticles: StartAction()
}