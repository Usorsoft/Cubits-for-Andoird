package com.mp.cubit.foundation

import android.os.Handler
import android.os.Looper

/**
 * File created by Michael Pankraz on 12.07.20.
 * <p>
 * Copyright by Michael Pankraz
 */

/**
 * This runs code on the main [Looper].
 */
fun runOnMain(codeBlock: () -> Unit) {
    Handler(Looper.getMainLooper()).post(codeBlock)
}

/**
 * This serves as the counter part of [repeat]. It counts down backwards from [times] to zero.
 */
inline fun repeatReversed(times: Int, action: (Int) -> Unit) {
    repeat(times) { count ->
        action(times - count)
    }
}