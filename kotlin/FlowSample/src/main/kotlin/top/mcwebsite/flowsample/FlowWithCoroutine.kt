package top.mcwebsite.flowsample

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime


/**
 * buffer
 */
@OptIn(ExperimentalTime::class)
suspend fun simpleFlowBuffer() {
    println("--------simple flow buffer-----------")
    val time = measureTime {
        flow {
            for (i in 1..5) {
                delay(1000)
                emit(i)
            }
        }
            .buffer()
            .collect {
                delay(300)
                println(it)
            }
    }
    println("Collectd in $time")
}


suspend fun main() {
    simpleFlowBuffer()
}