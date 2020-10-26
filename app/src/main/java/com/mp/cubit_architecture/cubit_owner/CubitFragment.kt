package com.mp.cubit_architecture.cubit_owner

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.cubit.Cubit

abstract class CubitFragment<CUBIT: Cubit<STATE>, STATE: Any> :
    Fragment(),
    CubitOwner<CUBIT, STATE>
{
    override lateinit var cubit: CUBIT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initCubitOwner()
    }
}



