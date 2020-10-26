package com.mp.cubit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.mp.cubit.foundation.CubitDsl
import com.mp.cubit.foundation.StateBuilderScope

/**
 * This represents and composable view which is state aware buy using
 * [StateBuilder] or [StateBuilderOf].
 * <p>
 * Created by Michael Pankraz on 12.07.20.
 * <p>
 * Copyright by Michael Pankraz
 */
abstract class StateViewOf<CUBIT : Cubit<STATE>, STATE : Any>(val cubit: CUBIT)

/**
 * This rebuilds its [content] if the [Cubit.mutableState] of the [Cubit] changes and provides the
 * new [STATE] trough the [StateBuilderScope] ([content]).
 *
 * Use [buildCondition] to decide whether the [content] should be build or not.
 * <p>
 * Created by Michael Pankraz on 12.07.20.
 * <p>
 * Copyright by Michael Pankraz
 */
@CubitDsl
@Composable
fun <CUBIT : Cubit<STATE>, STATE : Any> StateViewOf<CUBIT, STATE>.StateBuilder(
    buildCondition: ((state: STATE) -> Boolean)? = null,
    content: StateBuilderScope<STATE>
) {
    val state by cubit.mutableState
    val shouldBuild = buildCondition?.invoke(state) ?: true
    if (shouldBuild) content(state)
}

/**
 * This rebuilds its [content] if the [Cubit.mutableState] of the [Cubit] changes and the state is
 * of type [STATE_TYPE]. It provides the new [STATE_TYPE] trough the [StateBuilderScope] ([content]).
 * <p>
 * Created by Michael Pankraz on 12.07.20.
 * <p>
 * Copyright by Michael Pankraz
 */
@CubitDsl
@Composable
inline fun <reified STATE_TYPE : Any> StateViewOf<*, *>.StateBuilderOf(
    content: @Composable (state: STATE_TYPE) -> Unit
) {
    val state by cubit.mutableState
    if (state is STATE_TYPE) content(state as STATE_TYPE)
}