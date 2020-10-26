package com.mp.cubit_architecture.foundation

import android.os.Handler
import android.os.Looper

fun runOnMain(codeBlock: () -> Unit) {
    Handler(Looper.getMainLooper()).post(codeBlock)
}

inline fun repeatReversed(times: Int, action: (Int) -> Unit) {
    repeat(times) { count ->
        action(times - count)
    }
}

inline fun <reified T> Any.to(): T? {
    return if (this is T) this else null
}