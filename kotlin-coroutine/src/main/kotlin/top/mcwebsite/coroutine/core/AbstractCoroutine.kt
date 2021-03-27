package top.mcwebsite.coroutine.core

import top.mcwebsite.coroutine.Job
import top.mcwebsite.coroutine.OnCancel
import top.mcwebsite.coroutine.OnComplete
import top.mcwebsite.coroutine.cancel.suspendCancellableCoroutine
import top.mcwebsite.coroutine.scope.CoroutineScope
import java.lang.IllegalStateException
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.*
import kotlin.coroutines.cancellation.CancellationException

@ExperimentalStdlibApi
abstract class AbstractCoroutine<T>(context: CoroutineContext) : Job, Continuation<T>, CoroutineScope {

    protected val state = AtomicReference<CoroutineState>()

    override val context: CoroutineContext

    override val scopeContext: CoroutineContext = context

    private val parentJob = context[Job]

    init {
        state.set(CoroutineState.InComplete())
        this.context = context + this
    }

    protected open fun handleJobException(e: Throwable) = false

    val isCompleted
        get() = state.get() is CoroutineState.Complete<*>

    override val isActive: Boolean
        get() = when (state.get()) {
            is CoroutineState.Complete<*>,
            is CoroutineState.Cancelling -> false
            else -> true
        }

    override fun invokeOnComplete(onComplete: OnComplete): Disposable {
        return doOnCompleted { _ -> onComplete() }
    }

    protected fun doOnCompleted(block: (Result<T>) -> Unit): Disposable {
        val disposable = CompletionHandlerDisposable(this, block)
        val newState = state.updateAndGet { prev ->
            when (prev) {
                is CoroutineState.InComplete -> {
                    CoroutineState.InComplete().from(prev).with(disposable)
                }
                is CoroutineState.Cancelling -> {
                    CoroutineState.Cancelling().from(prev).with(disposable)
                }
                is CoroutineState.Complete<*> -> prev
            }
        }
        (newState as? CoroutineState.Complete<T>)?.let {
            block(
                when {
                    it.exception != null -> Result.failure(it.exception)
                    it.value != null -> Result.success(it.value)
                    else -> throw IllegalStateException("won't happen.")
                }
            )
        }
        return disposable
    }

    override fun remove(disposable: Disposable) {
        state.updateAndGet { prev ->
            when (prev) {
                is CoroutineState.InComplete -> {
                    CoroutineState.InComplete().from(prev).without(disposable)
                }
                is CoroutineState.Cancelling -> {
                    CoroutineState.Cancelling().from(prev).without(disposable)
                }
                is CoroutineState.Complete<*> -> {
                    prev
                }
            }
        }
    }

    private fun tryHandleException(e: Throwable): Boolean {
        return when(e) {
            is CancellationException -> {
                false
            }
            else -> {
                (parentJob as? AbstractCoroutine<*>)?.handleJobException(e)
                    ?.takeIf { it }
                    ?: handleChildException(e)
            }
        }
    }

    protected open fun handleChildException(e: Throwable): Boolean {
        cancel()
        return tryHandleException(e)
    }

    override fun resumeWith(result: Result<T>) {
        val newState = state.updateAndGet { prev ->
            when (prev) {
                is CoroutineState.Cancelling,
                is CoroutineState.InComplete -> {
                    CoroutineState.Complete(result.getOrNull(), result.exceptionOrNull()).from(prev)
                }
                is CoroutineState.Complete<*> -> {
                    throw IllegalStateException("Already completed!")
                }
            }
        }
        (newState as CoroutineState.Complete<T>).exception?.let(this::tryHandleException)
        newState.notifyCompletion(result)
        newState.clear()
    }

    override suspend fun join() {
        when (state.get()) {
            is CoroutineState.InComplete,
            is CoroutineState.Cancelling -> return joinSuspend()
            is CoroutineState.Complete<*> -> {
                println("Complete")
                val currentCallingJobState = coroutineContext[Job]?.isActive ?: return
                val a = this
                val b = coroutineContext[Job]!!
                if (a == b) {
                    println("equals")
                }
                if (!currentCallingJobState) {
                    throw CancellationException("Coroutine is cancelled.")
                }
                return
            }
        }
    }

    private suspend fun joinSuspend() = suspendCancellableCoroutine<Unit> { continuation ->
        println("joinSuspend")
        val disposable = doOnCompleted {
            continuation.resume(Unit)
        }
        continuation.invokeOnCancellation { disposable.disposable() }
    }

    override fun invokeOnCancel(onCancel: OnCancel): Disposable {
        val disposable = CancellationHandlerDisposable(this, onCancel)
        val newState = state.getAndUpdate { prev ->
            when (prev) {
                is CoroutineState.InComplete -> {
                    CoroutineState.InComplete().from(prev).with(disposable)
                }
                is CoroutineState.Cancelling,
                is CoroutineState.Complete<*> -> prev
            }
        }
        (newState as? CoroutineState.Cancelling)?.let {
            onCancel()
        }

        return disposable
    }

    override fun cancel() {
        val prevState = state.getAndUpdate { prev ->
            when (prev) {
                is CoroutineState.InComplete -> {
                    CoroutineState.Cancelling()
                }
                is CoroutineState.Cancelling,
                is CoroutineState.Complete<*> -> prev
            }
        }

        if (prevState is CoroutineState.InComplete) {
            prevState.notifyCancellation()
            prevState.clear()
        }

    }

}