package com.mp.cubit_architecture.cubit_owner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cubit.Cubit

/**
 * This is a [Cubit] aware [AppCompatActivity].
 *
 * Created by Michael Pankraz on 12.07.20.
 * <p>
 * Copyright by Michael Pankraz
 */
abstract class CubitActivity<CUBIT : Cubit<STATE>, STATE : Any> :
    AppCompatActivity(),
    CubitOwner<CUBIT, STATE> {
    override lateinit var cubit: CUBIT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initCubitOwner()
    }
}