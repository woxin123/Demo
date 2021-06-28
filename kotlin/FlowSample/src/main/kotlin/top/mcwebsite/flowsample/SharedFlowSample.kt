package top.mcwebsite.flowsample

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

suspend fun simpleSharedFlow() {
    val sharedFlow = MutableSharedFlow<Int>(
        replay = 5,
        extraBufferCapacity = 3,
    )
    coroutineScope {
        launch {
            sharedFlow.collect {
                println("collect1 received shared flow $it")
            }
        }
        launch {
            (1..10).forEach {
                sharedFlow.emit(it)
                delay(100)
            }
        }
        // wait a minute
        delay(1000)
        launch {
            sharedFlow.collect {
                println("collect2 received shared flow $it")
            }
        }
    }
}

fun currTime() = System.currentTimeMillis()

suspend fun simpleConvertToSharedFlow(started: SharingStarted) {
    var start = 0L
    // create normal flow
    val flow = (1..10).asFlow()
        .onStart { start = currTime() }
        .onEach {
            println("Emit $it ${currTime() - start}ms")
            delay(100)
        }
    // convert to shared flow
    // need coroutine scope
    coroutineScope {
        val sharedFlow = flow.shareIn(this, started, replay = 2)
        val job = launch {
            println("current time ")
            sharedFlow.collect {
                println("received convert shared flow $it at ${currTime() - start}ms")
            }
        }

        launch {
            delay(1000L)
            job.cancel()
            delay(310L)
            sharedFlow.collect {
                println("received again shared flow $it")
            }
            println("shared flow has stop")
        }
    }
}

@OptIn(ExperimentalTime::class)
suspend fun main() {
//    simpleSharedFlow()
    simpleConvertToSharedFlow(
        SharingStarted.WhileSubscribed(
            stopTimeout = 100L.toDuration(DurationUnit.MILLISECONDS),
            replayExpiration = 200L.toDuration(DurationUnit.MILLISECONDS)
        )
    )
}