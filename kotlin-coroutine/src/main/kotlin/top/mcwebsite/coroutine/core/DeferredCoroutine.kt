package top.mcwebsite.coroutine.core

import top.mcwebsite.coroutine.Deferred
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.suspendCoroutine

@ExperimentalStdlibApi
class DeferredCoroutine<T>(context: CoroutineContext): AbstractCoroutine<T>(context), Deferred<T> {

    override suspend fun await(): T {
        val currentState = state.get()
        return when (currentState) {
            is CoroutineState.InComplete,
                is CoroutineState.Cancelling -> awaitSuspend()
            is CoroutineState.Complete<*> -> {
                currentState.exception?.let { throw it } ?: (currentState.value as T)
            }
        }
    }

    private suspend fun awaitSuspend() = suspendCoroutine<T> { continuation ->
        doOnCompleted { result ->
            continuation.resumeWith(result)
        }
    }
}