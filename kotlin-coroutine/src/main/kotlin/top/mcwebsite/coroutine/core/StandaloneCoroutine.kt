package top.mcwebsite.coroutine.core

import top.mcwebsite.coroutine.exception.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

@ExperimentalStdlibApi
class StandaloneCoroutine(context: CoroutineContext): AbstractCoroutine<Unit>(context) {
    override fun handleJobException(e: Throwable): Boolean {
        super.handleJobException(e)
        context[CoroutineExceptionHandler]?.handleException(context, e)
            ?: Thread.currentThread().let {
                it.uncaughtExceptionHandler.uncaughtException(it, e)
            }
        return true
    }
}