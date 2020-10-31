package com.mp.cubit.scope

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.mp.cubit.Cubit
import com.mp.cubit.foundation.CubitProvider
import kotlin.reflect.KClass

/**
 * This provides a [Cubit] for a [LifecycleOwner] and ensures that the [Cubit] survives its
 * configuration changes.
 */
object ActivityScope {
    private val storage = mutableMapOf<String, Cubit<*>>()

    fun <CUBIT : Cubit<STATE>, STATE : Any> provide(
        kClass: KClass<CUBIT>,
        lifecycleOwner: AppCompatActivity,
        identifier: String = "",
        onCreate: CubitProvider<CUBIT>
    ): CUBIT {
        val key = keyOf(kClass, lifecycleOwner, identifier)
        val cubit = storage[key] ?: onCreate(kClass.java)

        lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                if (!lifecycleOwner.isChangingConfigurations && lifecycleOwner.isFinishing) {
                    storage[key]?.dispose()
                    storage.remove(key)
                }
            }
        })

        storage[key] = cubit
        return cubit as CUBIT
    }

    fun <CUBIT : Cubit<STATE>, STATE : Any> dispose(
        kClass: KClass<CUBIT>,
        lifecycleOwner: AppCompatActivity,
        identifier: String? = ""
    ) {
        val key = keyOf(kClass, lifecycleOwner, identifier)
        storage[key]?.dispose()
        storage.remove(key)
    }

    private fun <CUBIT : Cubit<STATE>, STATE : Any> keyOf(
        kClass: KClass<CUBIT>,
        lifecycleOwner: AppCompatActivity,
        identifier: String? = null
    ): String {
        return "${lifecycleOwner::class.java.simpleName}:${kClass.simpleName}:$identifier"
    }
}