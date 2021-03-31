package top.mcwebsite.kotlin.demo.coroutine

import top.mcwebsite.kotlin.demo.coroutine.util.executor.delay
import top.mcwebsite.kotlin.demo.coroutine.util.executor.delayExecutor
import java.util.concurrent.TimeUnit
import kotlin.coroutines.*

fun <R, T> launchCoroutine(receiver: R, block: suspend R.() -> T) {
    block.startCoroutine(receiver, object : Continuation<T> {
        override fun resumeWith(result: Result<T>) {
            println("Coroutine End: $result")
        }

        override val context: CoroutineContext = EmptyCoroutineContext
    })

}

class ProduceScope<T> {
    suspend fun produce(value: T) {
        // ...
        println("handler $value")
    }

}

fun callLaunchCoroutine() {
    launchCoroutine(ProduceScope<Int>()) {
        println("In Coroutine.")
        produce(1024)
        delay(1000)
        produce(2048)
    }
}

suspend fun delay(ms: Long) = suspendCoroutine<Unit> {
    delayExecutor.schedule({
        it.resume(Unit)
    }, ms, TimeUnit.MILLISECONDS)
}

fun main() {
    callLaunchCoroutine()
}