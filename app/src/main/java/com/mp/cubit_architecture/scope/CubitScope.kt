package com.mp.cubit_architecture.scope

import com.example.cubit.Cubit
import com.mp.cubit_architecture.foundation.CubitProvider
import kotlin.reflect.KClass

interface CubitScope {
    fun <CUBIT : Cubit<STATE>, STATE : Any> provide(
        kClass: KClass<CUBIT>,
        identifier: String?,
        onCreate: CubitProvider<CUBIT>,
    ): CUBIT
}
