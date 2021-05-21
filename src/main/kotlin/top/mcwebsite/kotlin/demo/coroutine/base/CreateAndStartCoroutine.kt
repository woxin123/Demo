package top.mcwebsite.kotlin.demo.coroutine.base

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine

fun main() {
    suspend {
        println("In Coroutine.")
        5
    }.startCoroutine(object : Continuation<Int> {

        override fun resumeWith(result: Result<Int>) {
            println("Coroutine End: $result")
        }

        override val context: CoroutineContext = EmptyCoroutineContext
    })
}