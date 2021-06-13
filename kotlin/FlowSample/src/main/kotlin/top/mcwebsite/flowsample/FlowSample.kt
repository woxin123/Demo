package top.mcwebsite.flowsample

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

suspend fun simpleFlow() {
    flow {
        emit(1)
        emit(2)
    }.collect {
        println(it)
    }
}

suspend fun simpleFlowOf() {
    println("------flowOf ------")
    flowOf(1, 2, 3, 4, 5)
        .onEach {
            delay(it * 100L)
        }
        .collect {
            println(it)
        }
}

suspend fun simpleAsFlow() {
    println("------list as flow-------")
    listOf(1, 2, 3).asFlow()
        .collect {
            println(it)
        }
    arrayOf('a', 'b', 'c')
        .asFlow()
        .collect {
            println(it)
        }
}

suspend fun simpleSwitchCoroutine() {
    println("------simple switch coroutine------")
    flow {
        for (i in 1..5) {
            delay(100L)
            emit(i)
        }
    }
        .map {
            it * it
        }
        .flowOn(Dispatchers.IO)
        .collect {
            println(it)
        }
}

suspend fun asFlowExample() {
    println("-------as flow example ---");
    {
       1 + 1
    }
    .asFlow()
        .collect {
            println(it)
        }
    suspend {
        delay(1000)
        1 + 1
    }
        .asFlow()
        .collect {
            println(it)
        }
}

/**
 * channel 是 Hot Stream
 */
suspend fun simpleChannelFlow() {
    println("-----channel flow------")
    channelFlow {
        for (i in 1..5) {
            delay(i * 100L)
            send(i)
        }
    }.collect {
        println(it)
    }
}

/**
 * terminal operate single
 * if has more than one, will throw exception
 *      - java.lang.IllegalArgumentException: Flow has more than one element
 */
suspend fun simpleFlowTerminalSingle() {
    println("------flow terminal single")
    val singleValue = flowOf(1)
        .single()
    println("single value = $singleValue")

//    flowOf(1, 2)
//        .single()
}

/**
 * terminal operate first
 * it will be return first value of flow
 * unlike single, if there is more than one value in flow, first will not throw an exception
 */
suspend fun simpleFlowTerminalFirst() {
    println("----flow terminal first-------")
    val firstValue = flowOf(1, 2, 3)
        .first()
    println("first value = $firstValue")
}

suspend fun simpleFlowTerminalToList() {
    println("----flow terminal toList------")
    val list = flowOf(1, 2, 3)
        .toList()
    println("list = $list")
    val myList = mutableListOf(100, 200)
    val myList2 = flowOf(1, 2, 3)
        .toList(myList)
    println("add myList as param res = $myList2")
}

/**
 * Accumulates value starting with initial value and applying operation current accumulator value and each element
 */
suspend fun simpleFlowTerminalFold() {
    println("----flow terminal fold-------")
    val sum = flowOf(1, 2, 3)
        .fold(0) { acc, value ->
            acc + value
        }

    println("use flow fold to sum, result = $sum")
}

/**
 * Accumulates value starting with the first element and
 * applying operation to current accumulator value and each element.
 * Throws NoSuchElementException if flow was empty.
 */
suspend fun simpleFlowTerminalReduce() {
    println("-----flow terminal reduce-----")
    val sum = flowOf(1, 2, 3)
        .reduce { accumulator, value ->
            accumulator + value
        }
    println("use flow reduce to sum, result = $sum")
}


suspend fun main() {
    simpleFlow()
    simpleAsFlow()
    simpleFlowOf()
    simpleChannelFlow()
    simpleSwitchCoroutine()
    asFlowExample()
    simpleFlowTerminalSingle()
    simpleFlowTerminalFirst()
    simpleFlowTerminalToList()
    simpleFlowTerminalFold()
    simpleFlowTerminalReduce()
}