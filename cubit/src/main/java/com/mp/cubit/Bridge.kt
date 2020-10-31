package com.mp.cubit

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.mp.cubit.foundation.CubitDsl
import com.mp.cubit.foundation.CubitProvider
import com.mp.cubit.scope.*
import kotlin.reflect.KClass

/**
 * This provides a [Cubit] from the [LifecycleOwnerScope] store if there is one, otherwise
 * it creates a new one via [onCreate] and provides this one.
 *
 * Use this to keep the [Cubit] alive trough configuration changes.
 *
 * Specify a [identifier] if you want to have more than on [Cubit] of the same type
 * per [lifecycleOwner].
 *
 * Created by Michael Pankraz on 12.07.20.
 * <p>
 * Copyright by Michael Pankraz
 */
@CubitDsl
fun <C : Cubit<S>, S : Any> Cubit.Companion.of(
    lifecycleOwner: LifecycleOwner,
    cubit: KClass<C>,
    identifier: String? = null,
    onCreate: CubitProvider<C>
): C {
    return LifecycleOwnerScope.provide(cubit, lifecycleOwner, identifier ?: "", onCreate)
}

/**
 * This provides a [Cubit] from the [ActivityScope] store if there is one, otherwise
 * it creates a new one via [onCreate] and provides this one.
 *
 * Use this to keep the [Cubit] alive trough configuration changes.
 *
 * Specify a [identifier] if you want to have more than on [Cubit] of the same type
 * per [lifecycleOwner].
 *
 * Created by Michael Pankraz on 12.07.20.
 * <p>
 * Copyright by Michael Pankraz
 */
@CubitDsl
fun <C : Cubit<S>, S : Any> Cubit.Companion.of(
    lifecycleOwner: AppCompatActivity,
    cubit: KClass<C>,
    identifier: String? = null,
    onCreate: CubitProvider<C>
): C {
    return ActivityScope.provide(cubit, lifecycleOwner, identifier ?: "", onCreate)
}

/**
 * This provides a [Cubit] from the [scope] store if there is one, otherwise
 * it creates a new one via [onCreate] and provides this one.
 *
 * Use this to keep the [Cubit] alive as long the [CubitScope] allows.
 * Available scopes: [GlobalScope], [ManualScope]
 *
 * Specify a [identifier] if you want to have more than on [Cubit] of the same type
 * per [scope].
 *
 * Created by Michael Pankraz on 12.07.20.
 * <p>
 * Copyright by Michael Pankraz
 */
@CubitDsl
fun <C : Cubit<S>, S : Any> Cubit.Companion.of(
    scope: CubitScope,
    cubit: KClass<C>,
    identifier: String? = null,
    onCreate: CubitProvider<C>
): C {
    return scope.provide(cubit, identifier, onCreate)
}
