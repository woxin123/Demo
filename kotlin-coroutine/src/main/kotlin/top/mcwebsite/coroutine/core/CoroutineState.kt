package top.mcwebsite.coroutine.core

import top.mcwebsite.coroutine.Job

sealed class CoroutineState {

    class InComplete : CoroutineState()
    class Cancelling : CoroutineState()
    class Complete<T>(val value: T? = null, val exception: Throwable? = null) : CoroutineState()

    protected var disposableList: RecursiveList<Disposable> = RecursiveList.Nil
        private set

    protected var children: RecursiveList<Job> = RecursiveList.Nil

    fun from(state: CoroutineState): CoroutineState {
        this.disposableList = state.disposableList
        this.children = children
        return this
    }

    fun with(element: Any): CoroutineState {
        when (element) {
            is Disposable -> this.disposableList = RecursiveList.Cons(element, this.disposableList)
            is Job -> this.children = RecursiveList.Cons(element, this.children)
        }
        return this
    }

    fun without(element: Any): CoroutineState {
        when(element) {
            is Disposable -> this.disposableList = this.disposableList.remove(element)
            is Job -> this.children = this.children.remove(element)
        }
        return this
    }

    fun clear() {
        this.disposableList = RecursiveList.Nil
        this.children = RecursiveList.Nil
    }

    fun <T> notifyCompletion(result: Result<T>) {
        this.disposableList.loopOn<CompletionHandlerDisposable<T>> {
            it.onComplete(result)
        }
    }

    fun notifyCancellation() {
        disposableList.loopOn<CancellationHandlerDisposable> {
            it.onCancel()
        }
    }
}