package top.mcwebsite.kotlin.demo.coroutine.base

import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun notSuspend(): Int {
    return suspendCoroutine<Int> { continuation ->
        continuation.resume(100)
    }
}