package com.mp.cubit.scope

import com.mp.cubit.Cubit
import com.mp.cubit.foundation.CubitProvider
import kotlin.reflect.KClass

/**
 * This is the base of the most cubit scope stores.
 * Inheritors: [GlobalScope], [ManualScope]
 *
 * Created by Michael Pankraz on 12.07.20.
 * <p>
 * Copyright by Michael Pankraz
 */
interface CubitScope {
    fun <CUBIT : Cubit<STATE>, STATE : Any> provide(
        kClass: KClass<CUBIT>,
        identifier: String?,
        onCreate: CubitProvider<CUBIT>,
    ): CUBIT
}
