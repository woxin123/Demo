package top.mcwebsite.kotlin.demo.coroutine.util.executor

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

val ioExecutor =
    Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
val delayExecutor = Executors.newScheduledThreadPool(1)

fun delay(ms: Long, block: () -> Unit) {
    delayExecutor.schedule(block, ms, TimeUnit.MILLISECONDS)
}