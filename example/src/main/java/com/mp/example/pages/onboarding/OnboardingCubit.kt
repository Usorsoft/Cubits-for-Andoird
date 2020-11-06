package com.jamitlabs.remoteui_sdk.onboarding

import android.view.View.GONE
import android.view.View.VISIBLE
import com.jamitlabs.remoteui_sdk.onboarding.OnboardingAction.ShowArticles
import com.jamitlabs.remoteui_sdk.repositories.RuntimeStore
import com.mp.cubit.Cubit

/**
 * This represents the logic component of the [OnboardingActivity].
 * <p>
 * Created by Michael Pankraz on 21.09.20.
 * <p>
 * Copyright EnBW AG
 */
class OnboardingCubit(private val runtimeStore: RuntimeStore) : Cubit<OnboardingCubit.State>() {

    override fun initState() = State.Initial

    sealed class State(val loadingVisibility: Int) {
        object Initial : State(GONE)
        object Loading : State(VISIBLE)
        object ShowOnboarding : State(GONE)
        object ShowContent : State(GONE)
        object NavigateBack : State(VISIBLE)
    }

    fun showArticles() {
        emitAction(ShowArticles)
    }

    override fun onDispose() {}
}