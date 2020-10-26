package com.mp.cubit.cubit_owner

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mp.cubit.Cubit

/**
 * This is a [Cubit] aware [Fragment].
 *
 * Created by Michael Pankraz on 12.07.20.
 * <p>
 * Copyright by Michael Pankraz
 */
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



