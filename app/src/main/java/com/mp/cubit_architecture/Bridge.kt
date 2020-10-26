package com.mp.cubit_architecture

import androidx.lifecycle.LifecycleOwner
import com.example.cubit.Cubit
import com.mp.cubit_architecture.foundation.CubitDsl
import com.mp.cubit_architecture.foundation.CubitProvider
import com.mp.cubit_architecture.scope.CubitScope
import com.mp.cubit_architecture.scope.LifecycleOwnerScope
import kotlin.reflect.KClass

@CubitDsl
fun <C : Cubit<S>, S : Any> Cubit.Companion.of(
    lifecycleOwner: LifecycleOwner,
    cubit: KClass<C>,
    identifier: String? = null,
    onCreate: CubitProvider<C>
): C {
    return LifecycleOwnerScope.provide(cubit, lifecycleOwner, identifier ?: "", onCreate)
}

@CubitDsl
fun <C : Cubit<S>, S : Any> Cubit.Companion.of(
    scope: CubitScope,
    cubit: KClass<C>,
    identifier: String? = null,
    onCreate: CubitProvider<C>
): C {
    return scope.provide(cubit, identifier, onCreate)
}
