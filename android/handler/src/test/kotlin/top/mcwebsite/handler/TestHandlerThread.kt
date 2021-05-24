package top.mcwebsite.handler

import org.junit.Test


fun main() {
    val handlerThread = HandlerThread("test")
    handlerThread.start()
    while (handlerThread.getLooper() == null) {
        Thread.yield()
    }
    val handler = handlerThread.getThreadHandler()
    handler?.post {
        println("Hello World!")
        println("current thread name = ${Thread.currentThread().name}")
    }
}
