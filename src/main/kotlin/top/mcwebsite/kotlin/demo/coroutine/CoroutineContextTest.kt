package top.mcwebsite.kotlin.demo.coroutine

import java.lang.ArithmeticException
import kotlin.coroutines.*

// EmptyCoroutineContext 标准库中定义的空的协程上下文
var coroutineContext: CoroutineContext = EmptyCoroutineContext

class CoroutineName(val name: String): AbstractCoroutineContextElement(Key) {
    companion object Key: CoroutineContext.Key<CoroutineName>

    override fun toString() = name
}

class CoroutineMessage(var message: String): AbstractCoroutineContextElement(Key) {
    companion object Key: CoroutineContext.Key<CoroutineMessage>

    override fun toString(): String {
        return "message = $message"
    }
}

class CoroutineExceptionHandler(val onErrorAction: (Throwable) -> Unit)
    : AbstractCoroutineContextElement(Key) {
    companion object Key: CoroutineContext.Key<CoroutineExceptionHandler>

    fun onError(error: Throwable) {
        error.printStackTrace()
        onErrorAction(error)
    }
}

fun main() {
    var list: List<Int> = emptyList()
    list += 0
    list += listOf(1, 2, 3)

    var coroutineContext: CoroutineContext = EmptyCoroutineContext
    coroutineContext += CoroutineName("co-01")
    coroutineContext += CoroutineExceptionHandler {
        print(it)
    }

    var nContext: CoroutineContext = CoroutineMessage("abcd")
    nContext += coroutineContext
    var a = 1 + 1
    suspend {
        println("In Coroutine [${coroutineContext[CoroutineName]}].")
        throw ArithmeticException()
        100
    }.startCoroutine(object : Continuation<Int> {
        override val context: CoroutineContext = coroutineContext

        override fun resumeWith(result: Result<Int>) {
            result.onFailure {
                context[CoroutineExceptionHandler]?.onError(it)
            }.onSuccess {
                println("Result $it")
            }
        }
    })
}