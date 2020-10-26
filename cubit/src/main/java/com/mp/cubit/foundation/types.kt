package com.mp.cubit.foundation

import androidx.compose.runtime.Composable

/**
 * File created by Michael Pankraz on 12.07.20.
 * <p>
 * Copyright by Michael Pankraz
 */

/**
 * Callback which provides the [Cubit] that matches the [Class].
 */
typealias CubitProvider<CUBIT> = (Class<CUBIT>) -> CUBIT

/**
 * This is a callback to be notified about changes.
 */
typealias ChangeListener<G> = (G) -> Unit

/**
 * This is a condition callback.
 */
typealias Condition = (Any) -> Boolean

/**
 * This provides a [STATE] to a [Composable].
 */
typealias StateBuilderScope<STATE> = @Composable (STATE) -> Unit

/**
 * This holds the matching [Condition] and action callback.
 */
class ActionBundle<G>(val condition: Condition, val callback: ChangeListener<G>)
