package com.mp.cubit.cubit_owner

import com.mp.cubit.Cubit

/**
 * This is a base interface for objects who wants to have a [Cubit] like [Activity] or [Fragment].
 *
 * Created by Michael Pankraz on 12.07.20.
 * <p>
 * Copyright by Michael Pankraz
 */
interface CubitOwner<CUBIT: Cubit<STATE>, STATE: Any> {
    var cubit: CUBIT

    fun initCubitOwner() {
        cubit = onCreateCubit()
        cubit.emitCurrentState()
    }

    fun onCreateCubit(): CUBIT
}
