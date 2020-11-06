package com.mp.example

import android.content.Context
import com.mp.example.repositories.FakeArticlesRepository
import com.mp.example.repositories.PreferencesRepository
import com.mp.example.repositories.RuntimeStore
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
