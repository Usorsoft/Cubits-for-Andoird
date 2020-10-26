package com.mp.cubit_architecture.foundation

typealias CubitProvider<CUBIT> = (Class<CUBIT>) -> CUBIT

//TODO @ MichaelPankraz : Refactor namings
typealias StateChangeListener<STATE> = (STATE) -> Unit
typealias ActionCallback<T> = (T) -> Unit
typealias ActionMatcher = (Any) -> Boolean
class ActionCallbackPair<T>(val matcher: ActionMatcher, val callback: ActionCallback<T>)