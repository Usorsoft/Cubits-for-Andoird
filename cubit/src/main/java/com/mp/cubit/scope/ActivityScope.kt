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

    /**
     * This holds all data needed for binding a cubit to an [AppCompatActivity].
     */
    private data class ScopeData(
        val activity: AppCompatActivity?,
        val cubit: Cubit<*>,
        val lifecycleObserver: LifecycleObserver
    )

    /**
     * This stores all [ScopeData] bound to an identifier.
     */
    private val storage = mutableMapOf<String, ScopeData>()


    /**
     * This provides a [Cubit] matching to the given parameters if already exist in [storage].
     * Otherwise a new [Cubit] will be created, stored and the provided.
     */
    fun <CUBIT : Cubit<STATE>, STATE : Any> provide(
        kClass: KClass<CUBIT>,
        activity: AppCompatActivity,
        identifier: String = "",
        onCreate: CubitProvider<CUBIT>
    ): CUBIT {
        val key = buildKey(kClass, activity, identifier)

        storage[key]?.cubit?.let {
            return it as CUBIT
        }

        val data = ScopeData(
            activity = activity,
            cubit = onCreate(kClass.java),
            lifecycleObserver = createDestroyObserver(key)
        )

        activity.lifecycle.addObserver(data.lifecycleObserver)
        storage[key] = data
        return data.cubit as CUBIT
    }

    private fun createDestroyObserver(dataKey: String): LifecycleObserver {
        return object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                val data = storage[dataKey] ?: return
                val activity = data.activity
                val destroyCubit = activity?.isFinishing ?: true
                if (destroyCubit) disposeData(dataKey)
            }
        }
    }

    /**
     * This frees all references stored for with the giver [dataKey] and therefore removes
     * ths [Cubit].
     */
    private fun disposeData(dataKey: String) {
        val data = storage[dataKey] ?: return
        data.activity?.lifecycle?.removeObserver(data.lifecycleObserver)
        storage[dataKey]?.cubit?.dispose()
        storage.remove(dataKey)
    }

    fun <CUBIT : Cubit<STATE>, STATE : Any> dispose(
        kClass: KClass<CUBIT>,
        lifecycleOwner: AppCompatActivity,
        identifier: String? = ""
    ) {
        val key = buildKey(kClass, lifecycleOwner, identifier)
        disposeData(key)
    }

    private fun <CUBIT : Cubit<STATE>, STATE : Any> buildKey(
        kClass: KClass<CUBIT>,
        lifecycleOwner: AppCompatActivity,
        identifier: String? = null
    ): String {
        return "${lifecycleOwner::class.java.simpleName}:${kClass.simpleName}:$identifier"
    }
}