package top.mcwebsite.coroutine.dispatcher

import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

object DefaultDispatcher: Dispatcher {

    private val threadGroup = ThreadGroup("DefaultDispatcher")

    private val threadIndex = AtomicInteger(0)

    public val executor = Executors.newFixedThreadPool(10) { runnable ->
        Thread(threadGroup, runnable, "${threadGroup.name}--${threadIndex.getAndIncrement()}").apply { isDaemon = true }
    }

    override fun dispatcher(block: () -> Unit) {
        executor.submit(block)
    }
}

