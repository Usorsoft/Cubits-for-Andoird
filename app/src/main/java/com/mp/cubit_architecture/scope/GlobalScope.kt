package com.mp.cubit_architecture.scope

import com.example.cubit.Cubit
import com.mp.cubit_architecture.foundation.CubitProvider
import kotlin.reflect.KClass

/**
 * This provides [Cubit]s and ensures that they exist the complete life time of tha [Application].
 */
object GlobalScope: CubitScope {
    private val storage = mutableMapOf<String, Cubit<*>>()

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
}