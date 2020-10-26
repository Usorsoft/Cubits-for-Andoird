package com.mp.cubit.scope

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
object LifecycleOwnerScope {
    data class Data(val cubit: Cubit<*>, var destroyedAt: Long? = null)

    private val storage = mutableMapOf<String, Data>()
    private const val CUBIT_SURVIVE_DURATION_IN_MS = 2_000

    fun <CUBIT : Cubit<STATE>, STATE : Any> provide(
        kClass: KClass<CUBIT>,
        lifecycleOwner: LifecycleOwner,
        identifier: String = "",
        onCreate: CubitProvider<CUBIT>
    ): CUBIT {
        disposeExpiredCubits()
        val key = keyOf(kClass, lifecycleOwner, identifier)
        val data = storage[key] ?: Data(onCreate(kClass.java))
        data.destroyedAt = null

        lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                data.destroyedAt = System.currentTimeMillis()
            }
        })

        storage[key] = data
        return data.cubit as CUBIT
    }

    private fun disposeExpiredCubits() {
        val expirationTime = System.currentTimeMillis() - CUBIT_SURVIVE_DURATION_IN_MS

        val expiredEntries = storage.filter {
            val destroyedAt = it.value.destroyedAt ?: return@filter false
            expirationTime >= destroyedAt
        }

        expiredEntries.forEach {
            it.value.cubit.dispose()
            storage.remove(it.key)
        }
    }

    fun <CUBIT : Cubit<STATE>, STATE : Any> dispose(
        kClass: KClass<CUBIT>,
        lifecycleOwner: LifecycleOwner,
        identifier: String? = ""
    ) {
        val key = keyOf(kClass, lifecycleOwner, identifier)
        storage[key]?.cubit?.dispose()
        storage.remove(key)
    }

    private fun <CUBIT : Cubit<STATE>, STATE : Any> keyOf(
        kClass: KClass<CUBIT>,
        lifecycleOwner: LifecycleOwner,
        identifier: String? = null
    ): String {
        return "${lifecycleOwner::class.java.simpleName}:${kClass.simpleName}:$identifier"
    }
}