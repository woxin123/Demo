package top.mcwebsite.coroutine

import top.mcwebsite.coroutine.core.Disposable
import kotlin.coroutines.CoroutineContext

typealias OnCancel = () -> Unit
typealias OnComplete = () -> Unit

interface Job : CoroutineContext.Element {
    companion object Key : CoroutineContext.Key<Job>

    override val key: CoroutineContext.Key<*> get() = Job

    val isActive: Boolean

    fun invokeOnCancel(onCancel: OnCancel): Disposable

    fun invokeOnComplete(onComplete: OnComplete): Disposable

    fun cancel()

    fun remove(disposable: Disposable)

    suspend fun join()
}