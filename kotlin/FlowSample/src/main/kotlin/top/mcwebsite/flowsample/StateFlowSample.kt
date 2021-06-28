package top.mcwebsite.flowsample

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

private val state = MutableStateFlow(1)

suspend fun simpleStateFlow() {
    coroutineScope {
        launch {
            delay(1000)
            state.collect {
                println("before state value $it")
            }
        }
        launch {
            for (i in 1..100) {
                state.emit(i)
                delay(100)
            }
        }

        launch {
            state.collect {
                println("state value $it")
            }
        }
    }
}

suspend fun simpleHotStateFlow() {
    val state = MutableStateFlow(1)
    coroutineScope {
        launch {
            for (i in 0..10) {
                state.emit(i)
                delay(1000)
            }
        }

        launch {
            delay(2000)
            state.collect {
                println("receive state $it")
            }
        }
    }
}

private suspend fun simpleStateFlowAndMutableStateFlow() {
    val mutableStateFlow = MutableStateFlow(1)
    coroutineScope {
        launch {
            collectData(mutableStateFlow.asStateFlow())
        }
        launch {
            (1..10).forEach {
                delay(100)
                mutableStateFlow.emit(it)
            }
        }
    }
}

private suspend fun collectData(stateFlow: StateFlow<Int>) {
    stateFlow.collect {
        println("received data $it")
    }
}

suspend fun simpleCovertToStateFlow() {
    val flow = flow {
        for (i in 0..4) {
            emit(i)
            delay(100)
        }
    }
    coroutineScope {
        val stateFlow = flow.stateIn(this)
        launch {
            stateFlow.collect {
                println("receive flow.stateIn value $it")
            }
        }
        stateFlow.value
    }
}

suspend fun main() {
//    simpleStateFlow()
//    simpleHotStateFlow()
//    simpleCovertToStateFlow()
    simpleStateFlowAndMutableStateFlow()
}

