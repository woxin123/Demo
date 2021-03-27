package top.mcwebsite.kotlin.demo.coroutine.concurrent

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.sync.withPermit


suspend fun main() {
    mutexCounter()
    semaphoreCounter()
}

suspend fun mutexCounter() {
    var count = 0
    val mutex = Mutex()
    List(1000) {
        GlobalScope.launch {
            mutex.withLock {
                count++
            }
        }
    }.joinAll()
    println(count)
}

suspend fun semaphoreCounter() {
    var count = 0
    val semaphore = Semaphore(1)
    List(1000) {
        GlobalScope.launch {
            semaphore.withPermit {
                count++
            }
        }
    }.joinAll()
}

suspend fun pureFunctionCounter() {
    val count = 0
    val result = count + List(1000) {
        GlobalScope.async { 1 }
    }.map {
        it.await()
    }.sum()

    println(result  )
}