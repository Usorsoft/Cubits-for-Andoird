package com.mp.example.utils

import kotlin.reflect.KClass

object Args {
    private val storage = mutableMapOf<KClass<*>, Any>()
    private const val FALLBACK_NAME = "[FALLBACK_NAME]"

    fun provide(arg: Any) {
        val kClass = arg::class
        storage[kClass] = arg
    }

    fun <T: Any> consumeOrNull(kClass: KClass<T>): T? {
        val arg = storage[kClass] as? T
        storage.remove(kClass)
        return arg
    }

    fun <T: Any> consume(kClass: KClass<T>): T {
        return consumeOrNull(kClass) ?: throw TypeNotPresentException(kClass.simpleName, Error())
    }
}