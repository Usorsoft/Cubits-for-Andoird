package com.example.cubit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.mp.cubit_architecture.foundation.CubitDsl

typealias StateProvider<STATE> = @Composable (STATE) -> Unit

/**
 * This represents and composable view which is state aware buy using [StateBuilder] or  [StateBuilderOf].
 */
abstract class StateViewOf<CUBIT : Cubit<STATE>, STATE : Any>(val cubit: CUBIT)

@CubitDsl
@Composable
fun <CUBIT : Cubit<STATE>, STATE : Any> StateViewOf<CUBIT, STATE>.StateBuilder(
    where: ((state: STATE) -> Boolean)? = null,
    content: StateProvider<STATE>
) {
    val state by cubit.mutableState
    val shouldBuild = where?.invoke(state) ?: true
    if (shouldBuild) content(state)
}

@CubitDsl
@Composable
inline fun <reified T : Any> StateViewOf<*, *>.StateBuilderOf(
    content: @Composable (state: T) -> Unit
) {
    val state by cubit.mutableState
    if (state is T) content(state as T)
}