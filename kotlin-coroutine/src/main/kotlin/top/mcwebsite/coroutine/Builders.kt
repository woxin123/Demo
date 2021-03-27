package top.mcwebsite.coroutine

import top.mcwebsite.coroutine.context.CoroutineName
import top.mcwebsite.coroutine.core.DeferredCoroutine
import top.mcwebsite.coroutine.core.StandaloneCoroutine
import top.mcwebsite.coroutine.dispatcher.Dispatchers
import top.mcwebsite.coroutine.scope.CoroutineScope
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.*

val coroutineIndex = AtomicInteger(0)

@ExperimentalStdlibApi
fun CoroutineScope.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit): Job {
    val completion = StandaloneCoroutine(newCoroutineContext(context))
    block.startCoroutine(completion, completion)
    return completion
}

@ExperimentalStdlibApi
fun <T> CoroutineScope.async(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> T): Deferred<T> {
    val completion = DeferredCoroutine<T>(newCoroutineContext(context))
    block.startCoroutine(completion, completion)
    return completion
}

fun CoroutineScope.newCoroutineContext(context: CoroutineContext): CoroutineContext {
    val combined = scopeContext + context + CoroutineName("@coroutine#${coroutineIndex.getAndIncrement()}")
    return if (combined !== Dispatchers.Default && combined[ContinuationInterceptor] == null) {
        combined + Dispatchers.Default
    } else {
        combined
    }
}



