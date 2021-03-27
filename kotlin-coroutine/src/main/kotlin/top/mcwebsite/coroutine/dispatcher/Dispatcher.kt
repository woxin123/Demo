package top.mcwebsite.coroutine.dispatcher

import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

interface Dispatcher {
    fun dispatcher(block: () -> Unit)
}

open class DispatcherContext(private val dispatcher: Dispatcher): AbstractCoroutineContextElement(ContinuationInterceptor), ContinuationInterceptor {
    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T>
        = DispatcherContinuation(continuation, dispatcher)
}

private class DispatcherContinuation<T>(val delegate: Continuation<T>, val dispatcher: Dispatcher): Continuation<T> {
    override val context = delegate.context

    override fun resumeWith(result: Result<T>) {
        dispatcher.dispatcher { delegate.resumeWith(result) }
    }
}