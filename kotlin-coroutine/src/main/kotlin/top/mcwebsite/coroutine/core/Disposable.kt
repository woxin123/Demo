package top.mcwebsite.coroutine.core

import top.mcwebsite.coroutine.Job
import top.mcwebsite.coroutine.OnCancel

typealias OnComplete<T> = (Result<T>) -> Unit

interface Disposable {
    fun disposable()
}

class CompletionHandlerDisposable<T>(val job: Job, val onComplete: OnComplete<T>): Disposable {
    override fun disposable() {
        job.remove(this)
    }
}

class CancellationHandlerDisposable(val job: Job, val onCancel: OnCancel): Disposable {
    override fun disposable() {
        job.remove(this)
    }
}