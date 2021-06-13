package top.mcwebsite.flowsample

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.onCompletion

/**
 * Flow can use try..catch to handle exception
 * but this way not recommend
 */
suspend fun useTryCatch() {
    flow {
        emit(1)
        try {
            throw RuntimeException()
        } catch (e: Exception) {
            e.stackTrace
        }
    }.onCompletion {
        println("Done")
    }.collect {
        println(it)
    }
}

/**
 * onCompletion can determine whether there is an exception,
 * can not catch exception
 */
suspend fun useOnCompletion() {
    println("--------use onCompletion--------")
    flow {
        emit(1)
        throw RuntimeException()
    }.onCompletion { cause ->
        if (cause != null) {
            println("Flow completed exceptionally")
        } else {
            println("Done")
        }
    }.collect { println(it) }
}

suspend fun useCatchOperation() {
    println("---------use catch operation--------")
    flow {
        emit(1)
        throw RuntimeException()
    }.onCompletion { cause ->
        if (cause != null) {
            println("Flow completed exceptionally")
        } else {
            println("Done")
        }
    }.catch {
        println("catch exception")
    }.collect {
        println(it)
    }
}

suspend fun main() {
    useTryCatch()
//    useOnCompletion()
    useCatchOperation()
}