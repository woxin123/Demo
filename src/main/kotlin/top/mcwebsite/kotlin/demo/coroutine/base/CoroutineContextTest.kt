package top.mcwebsite.kotlin.demo.coroutine.base

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

class CoroutineExceptionHandler(private val onErrorAction: (Throwable) -> Unit)
    : AbstractCoroutineContextElement(Key) {
    companion object Key: CoroutineContext.Key<CoroutineExceptionHandler>

    fun onError(error: Throwable) {
        error.printStackTrace()
        onErrorAction(error)
    }
}

fun main() {
    var coroutineContext: CoroutineContext = EmptyCoroutineContext
    coroutineContext += CoroutineName("简单的协程名")
    coroutineContext += CoroutineExceptionHandler {
        println("呀！出错了，${it.cause}")
    }
    suspend {
        println("协程执行中... 协程名为: [${coroutineContext[CoroutineName]}].")
        throw ArithmeticException()
        "协程执行结束"
    }.startCoroutine(object : Continuation<String> {
        override val context: CoroutineContext = coroutineContext

        override fun resumeWith(result: Result<String>) {
            result.onFailure {
                context[CoroutineExceptionHandler]?.onError(it)
            }.onSuccess {
                println("协程执行成功了!")
            }
        }
    })
}