package top.mcwebsite.kotlin.demo.coroutine.base

import kotlin.concurrent.thread
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.coroutines.*
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn

suspend fun suspendFunctionA() {
    println("我是一个挂起函数")
}


suspend fun suspendFunctionB()
        = suspendCoroutine<Int> { continuation ->
    thread {
        println("挂起函数执行中...")
        continuation.resumeWith(Result.success(5))
    }
}


suspend fun notSuspendFunction() = suspendCoroutine<Int> { continuation ->
    continuation.resume(200)
}

fun main() {
    suspend {
        suspendFunctionB()
        notSuspendFunction()
    }.startCoroutine(object : Continuation<Int> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Int>) {
            println("协程执行结束: $result")
        }

    })
}

