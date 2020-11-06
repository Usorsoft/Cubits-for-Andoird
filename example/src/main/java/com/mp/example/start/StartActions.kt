package com.jamitlabs.remoteui_sdk.start

sealed class StartAction {
    object ShowOnboarding: StartAction()
    object ShowArticles: StartAction()
}