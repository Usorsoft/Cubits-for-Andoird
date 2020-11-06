package com.jamitlabs.remoteui_sdk.onboarding

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.compose.ui.platform.setContent
import com.jamitlabs.remoteui_sdk.main.ArticlesActivity
import com.jamitlabs.remoteui_sdk.onboarding.OnboardingAction.ShowArticles
import com.jamitlabs.remoteui_sdk.repositories.PreferencesRepository
import com.jamitlabs.remoteui_sdk.repositories.RuntimeStore
import com.mp.cubit.Cubit
import com.mp.cubit.cubit_owner.CubitActivity
import com.mp.cubit.of
import org.koin.java.KoinJavaComponent
import org.koin.java.KoinJavaComponent.get


/**
 * Serves as the onboarding activity.
 * <p>
 * Created by Michael Pankraz on 21.09.20.
 * <p>
 * Copyright EnBW AG
 */
class OnboardingActivity : CubitActivity<OnboardingCubit, OnboardingCubit.State>() {

    lateinit var view: OnboardingView
    override fun onCreateCubit() = Cubit.of(this, OnboardingCubit::class) {
        OnboardingCubit(
            get(RuntimeStore::class.java)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = OnboardingView(cubit)
        cubit.addActionListener(::onAction, condition = { it is OnboardingAction })
        setContent { view.Compose() }
    }

    private fun onAction(action: OnboardingAction) {
        when (action) {
            is ShowArticles -> {
                ArticlesActivity.start(this)
                finish()
            }
        }
    }

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity.baseContext, OnboardingActivity::class.java)
            activity.startActivity(intent)
        }
    }
}