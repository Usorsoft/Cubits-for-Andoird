package com.jamitlabs.remoteui_sdk.start

import com.jamitlabs.remoteui_sdk.repositories.PreferencesRepository
import com.jamitlabs.remoteui_sdk.start.StartAction.*
import com.mp.cubit.Cubit

class StartCubit(private val prefs: PreferencesRepository) : Cubit<Unit>() {

    override fun initState() = Unit

    fun navigateNext() {
        // TODO: Start OnboardingActivity if app is starting the first time
        emitAction(ShowArticles)
    }

    override fun onDispose() {}
}