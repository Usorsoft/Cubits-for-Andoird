package com.mp.cubit_architecture.cubit_owner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cubit.Cubit

/**
 * Serves as a activity which observes state of a specific bloc.
 * <p>
 * Created by Michael Pankraz on 12.07.20.
 * <p>
 * Copyright EnBW AG
 */
abstract class CubitActivity<CUBIT: Cubit<STATE>, STATE: Any> :
    AppCompatActivity(),
    CubitOwner<CUBIT, STATE>
{
    override lateinit var cubit: CUBIT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initCubitOwner()
    }
}