package com.mp.example.start

import android.os.Bundle
import com.mp.cubit.Cubit
import com.mp.cubit.cubit_owner.CubitActivity
import com.mp.cubit.of
import com.mp.example.pages.articles.ArticlesActivity
import com.mp.example.pages.onboarding.OnboardingActivity
import com.mp.example.repositories.PreferencesRepository
import org.koin.java.KoinJavaComponent.get
import com.mp.example.start.StartAction.*

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