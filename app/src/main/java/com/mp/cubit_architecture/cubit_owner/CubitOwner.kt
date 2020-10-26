package com.mp.cubit_architecture.cubit_owner

import com.example.cubit.Cubit

interface CubitOwner<CUBIT: Cubit<STATE>, STATE: Any> {
    var cubit: CUBIT

    fun initCubitOwner() {
        cubit = onCreateCubit()
        cubit.emitCurrentState()
    }

    fun onCreateCubit(): CUBIT
}
