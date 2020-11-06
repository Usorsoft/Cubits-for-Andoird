package com.jamitlabs.remoteui_sdk

import android.content.Context
import com.jamitlabs.remoteui_sdk.repositories.PreferencesRepository
import com.jamitlabs.remoteui_sdk.main.ArticlesCubit
import com.jamitlabs.remoteui_sdk.onboarding.OnboardingCubit
import com.jamitlabs.remoteui_sdk.repositories.RuntimeStore
import com.jamitlabs.remoteui_sdk.start.StartCubit
import com.mp.example.repositories.FakeArticlesRepository
import org.koin.core.context.startKoin
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import kotlin.reflect.KClass

/**
 * This declares all [Koin] modules for dependency injection.
 * <p>
 * Created by Michael Pankraz on 24.09.20.
 * <p>
 * Copyright EnBW AG
 */
fun initDependencyInjection(context: Context) {
    startKoin {
        modules(
            repositories(context),
        )
    }
}

@kotlinx.coroutines.ExperimentalCoroutinesApi
private fun repositories(context: Context) = module {
    single { RuntimeStore() }
    single { FakeArticlesRepository() }
    factory { PreferencesRepository(context) }
}

fun <T : Any> Any.inject(
    kClass: KClass<T>, qualifier: Qualifier? = null,
    parameters: ParametersDefinition? = null
) = KoinJavaComponent.get(kClass.java, qualifier, parameters)
