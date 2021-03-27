package top.mcwebsite.coroutine.cancel

import top.mcwebsite.coroutine.OnCancel

sealed class CancelState {
    override fun toString(): String {
        return "CancelState.${this.javaClass.simpleName}"
    }

    object InComplete: CancelState()

    class CancelHandler(var onCancel: OnCancel): CancelState()

    class Complete<T>(val value: T? = null, val exception: Throwable? = null) : CancelState()

    object Cancelled: CancelState()

}
