package top.mcwebsite.kotlin.demo.coroutine.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import top.mcwebsite.kotlin.demo.coroutine.base.delay
import java.lang.ArithmeticException
import java.util.concurrent.Executors


fun createFlow() = flow<Int> {
    (1..3).forEach {
        emit(it)
        delay(1000)
    }
}.onEach { println(it) }

suspend fun flows() {
    val intFlow = flow<Int> {
        (1..3).forEach {
            emit(it)
            println("emit Thread name = ${Thread.currentThread().name}")
            delay(100)
        }
    }
    val dispatcher = Executors.newSingleThreadExecutor {
        Thread(it, "MyThread").also { it.isDaemon = true }
    }.asCoroutineDispatcher()

    GlobalScope.launch(dispatcher) {
        intFlow.flowOn(Dispatchers.IO)
            .collect { println("${Thread.currentThread().name} $it") }
    }.join()
    intFlow.onEach { }.launchIn(GlobalScope)

}

suspend fun exception() {
    flow {
        emit(1)
        throw ArithmeticException("Div 0")
    }.catch { t: Throwable ->
        println("caught error $t")
        emit(10)
    }.onCompletion { t: Throwable? ->
        println("finally.")
    }.flowOn(Dispatchers.IO)
        .collect { println(it) }
}

suspend fun cancelFlow() {
    val job = GlobalScope.launch {
        val intFlow = flow {
            (1..3).forEach {
                delay(1000L)
                emit(it)
            }
        }
        intFlow.collect { println(it) }
    }
    delay(2500L)
    job.cancelAndJoin()
}

// 生成元素的时候如果想要切换调度器就必须要使用 ChannelFlow
suspend fun fromChannel() {
    val channel = Channel<Int>()
    channel.consumeAsFlow()

    channelFlow {
        // good
        send(1)
        withContext(Dispatchers.IO) {
            send(2)
        }
    }.collect { println(it) }
}

// Flow 背压
suspend fun backPressure() {
    flow {
        List(100) {
            emit(it)
        }
    }
//        .buffer()
//        .conflate()
//        .collect { value ->
        .collectLatest { value ->
            println("Collecting $value")
            delay(100)
            println("$value collected")
        }
}

suspend fun transformFlow() {
    flow {
        List(5) { emit(it) }
    }.map {
        flow { List(it) { emit(it) } }
    }.flattenConcat()
        .collect { println(it) }
}

fun fromCollections() {
    listOf(1, 2, 3, 4, 5).asFlow()
    setOf(1, 2, 3, 4, 5).asFlow()
    flowOf(1, 2, 3, 4, 5)
}

suspend fun main() {
//    flows()
//    createFlow().launchIn(GlobalScope)
//    exception()
//    cancelFlow()
//    fromChannel()
//    backPressure()
    transformFlow()
}
