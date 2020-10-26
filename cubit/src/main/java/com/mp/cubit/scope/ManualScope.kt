package com.mp.cubit.scope

import com.mp.cubit.Cubit
import com.mp.cubit.foundation.CubitProvider
import kotlin.reflect.KClass

/**
 * This provides [Cubit]s and keeps them alive until a specific [Cubit] is disposed vai [dispose].
 *
 * Created by Michael Pankraz on 12.07.20.
 * <p>
 * Copyright by Michael Pankraz
 */
object ManualScope: CubitScope {
    private val storage = mutableMapOf<String, Cubit<*>>()
    private const val FALLBACK_NAME = "[FALLBACK_NAME]"

    override fun <CUBIT : Cubit<STATE>, STATE : Any> provide(
        kClass: KClass<CUBIT>,
        identifier: String?,
        onCreate: CubitProvider<CUBIT>
    ): CUBIT {
        val key = "${kClass::simpleName.name}:${identifier ?: ""}"
        val cubit = storage[key] ?: onCreate(kClass.java)
        storage[key] = cubit
        return cubit as CUBIT
    }

    fun <CUBIT : Cubit<STATE>, STATE : Any> dispose(kClass: KClass<CUBIT>) {
        val key = kClass.simpleName ?: FALLBACK_NAME
        val cubit = storage[key] ?: return
        cubit.dispose()
        storage.remove(key)
    }
}