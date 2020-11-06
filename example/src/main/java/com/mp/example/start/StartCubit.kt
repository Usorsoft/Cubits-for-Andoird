package com.mp.example.start

import com.mp.cubit.Cubit
import com.mp.example.pages.onboarding.OnboardingAction.*
import com.mp.example.repositories.PreferencesRepository

class StartCubit(private val prefs: PreferencesRepository) : Cubit<Unit>() {

    override fun initState() = Unit

    fun navigateNext() {
        // TODO: Start OnboardingActivity if app is starting the first time
        emitAction(ShowArticles)
    }

    override fun onDispose() {}
}