package top.mcwebsite.coroutine.scope

import top.mcwebsite.coroutine.core.AbstractCoroutine
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.startCoroutine
import kotlin.coroutines.suspendCoroutine

interface CoroutineScope {
    val scopeContext: CoroutineContext
}

internal class ContextScope(context: CoroutineContext): CoroutineScope {
    override val scopeContext = context
}

operator fun CoroutineScope.plus(context: CoroutineContext): CoroutineScope =
    ContextScope(scopeContext + context)



@ExperimentalStdlibApi
suspend fun <R> coroutineScope(block: suspend CoroutineScope.() ->R): R =
    suspendCoroutine { continuation ->
        val coroutine = ScopeCoroutine(continuation.context, continuation)
        block.startCoroutine(coroutine, coroutine)
    }

@ExperimentalStdlibApi
internal open class ScopeCoroutine<T>(
    context: CoroutineContext,
    protected val continuation: Continuation<T>
) : AbstractCoroutine<T>(context) {
    override fun resumeWith(result: Result<T>) {
        super.resumeWith(result)
        continuation.resumeWith(result)
    }
}