package com.mp.example.pages.onboarding

import android.view.View.GONE
import android.view.View.VISIBLE
import com.mp.cubit.Cubit
import com.mp.example.repositories.RuntimeStore

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
        emitAction(OnboardingAction.ShowArticles)
    }

    override fun onDispose() {}
}