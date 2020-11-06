package com.jamitlabs.remoteui_sdk

import android.app.Application
import com.mp.cubit.foundation.NavigationAction

/**
 * This is the [Application] object of the app.
 * <p>
 * Created by Michael Pankraz on 24.09.20.
 * <p>
 * Copyright EnBW AG
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDependencyInjection(applicationContext)
    }
}

object NavigateNext: NavigationAction()