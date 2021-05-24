package top.mcwebsite.handler

import java.lang.RuntimeException

class Looper(
    quitAllowed: Boolean = false
) {

    companion object {
        private val threadLocal = ThreadLocal<Looper>()
        lateinit var mainLooper: Looper

        fun prepare(quitAllowed: Boolean = true) {
            if (threadLocal.get() != null) {
                throw RuntimeException("Only one Looper may by created pre thread")
            }
            threadLocal.set(Looper(quitAllowed))
        }

        fun prepareMainLooper() {
            prepare(false)
            synchronized(Looper::class.java) {
                if (!this::mainLooper.isLateinit) {
                    throw IllegalArgumentException("The main Looper has already been prepared.")
                }
                mainLooper = myLooper()!!
            }
        }

        fun loop() {
            val me = myLooper() ?: throw RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.")
            val queue = me.queue

            while (true) {
                val msg = queue.next() ?: return
                msg.target?.dispatchMessage(msg)
                msg.recycleUnchecked()
            }
        }

        fun myLooper(): Looper? = threadLocal.get()
    }


    val queue: MessageQueue = MessageQueue(quitAllowed)
    val thread: Thread = Thread.currentThread()

    fun isCurrentThread() = thread == Thread.currentThread()

    fun quit() {
        queue.quit(false)
    }


}