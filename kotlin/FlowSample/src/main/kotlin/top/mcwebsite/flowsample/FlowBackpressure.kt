package top.mcwebsite.flowsample

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

/**
 * Backpressure
 * Flow 中的背压策略
 * - buffer
 *  - capacity
 *  - onBufferOverflow
 *      - SUSPEND
 *      - DROP_OLDEST
 *      - DROP_LATEST
 * - conflate
 */

suspend fun flowBackpressureBuffer(overflow: BufferOverflow) {
    fun currTime() = System.currentTimeMillis()
    var start: Long = 0
    val time = measureTimeMillis {
        (1..5).asFlow()
            .onStart { start = currTime() }
            .onEach {
                delay(100)
                println("Emit $it (${currTime() - start}ms)")
            }
            .buffer(capacity = 2, overflow)
            .collect {
                println("Collect $it starts (${currTime() - start}ms) ")
                delay(500)
                println("Collect $it ends (${currTime() - start}ms) ")
            }
    }
    println("Cost $time ms")
}

suspend fun flowBackpressureLatest() {
    fun curTime() = System.currentTimeMillis()
    var start: Long = 0
    val time = measureTimeMillis {
        (1..5).asFlow()
            .onStart { start = curTime() }
            .onEach {
                delay(100)
                println("Emit $it (${curTime() - start}ms)")
            }
            .conflate()
            .collect {
                println("Collect $it start (${curTime() - start}ms) ")
                delay(500)
                println("Collect $it ends ${curTime() - start}ms) ")
            }
    }
    println("Cost $time")
}

suspend fun main() {
//    flowBackpressureBuffer(BufferOverflow.SUSPEND)
//    flowBackpressureBuffer(BufferOverflow.DROP_LATEST)
//    flowBackpressureBuffer(BufferOverflow.DROP_OLDEST)
    flowBackpressureLatest()
}