package com.jamitlabs.remoteui_sdk.start

import android.os.Bundle
import com.jamitlabs.remoteui_sdk.main.ArticlesActivity
import com.jamitlabs.remoteui_sdk.onboarding.OnboardingActivity
import com.jamitlabs.remoteui_sdk.repositories.PreferencesRepository
import org.koin.java.KoinJavaComponent
import com.jamitlabs.remoteui_sdk.start.StartAction.*
import com.mp.cubit.Cubit
import com.mp.cubit.cubit_owner.CubitActivity
import com.mp.cubit.of
import org.koin.java.KoinJavaComponent.get

class StartActivity : CubitActivity<StartCubit, Unit>() {

    override fun onCreateCubit() = Cubit.of(this, StartCubit::class) {
        StartCubit(
            get(PreferencesRepository::class.java)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cubit.addActionListener(::onAction, condition = { it is StartAction })
        cubit.navigateNext()
    }

    private fun onAction(action: StartAction) {
        when (action) {
            is ShowOnboarding -> {
                // start onboarding activity
                OnboardingActivity.start(this)
                finish()
            }
            is ShowArticles -> {
                // start articles activity
                ArticlesActivity.start(this)
                finish()
            }
        }
    }
}