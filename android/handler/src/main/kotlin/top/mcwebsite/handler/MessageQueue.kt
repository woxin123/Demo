package top.mcwebsite.handler

import top.mcwebsite.handler.internal.DelayQueue
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class MessageQueue(
    private val quitAllowed: Boolean
) {
    private val queue by lazy {
        DelayQueue<Message>()
    }


    private val POSITION = Message()

    private var mQuiting: Boolean = false

    fun enqueueMessage(msg: Message, `when`: Long): Boolean {
        if (msg.target == null) {
            throw IllegalArgumentException("Message must be have a target")
        }
        if (msg.isInUse()) {
            throw IllegalArgumentException(msg.toString() + "This message is already in use.")
        }
        synchronized(this) {
            if (mQuiting) {
                val e = IllegalStateException(
                    msg.target.toString() + "send message to a Handler on a dead thread")
                println(e.message)
                e.printStackTrace()
                msg.recycle()
                return true
            }

            msg.markInUse()
            msg.`when` = `when`
            queue.add(msg)
        }
        return true
    }

    fun next(): Message? {
        try {
            val message = queue.take()
            if (message == POSITION) {
                return null
            }
            return message
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return null
    }

    fun removeMessages(h: Handler, what: Int? = null, obj: Any? = null, r: Runnable? = null) {
        queue.removeIf { msg ->
            val shouldRemoved =
                (what == null || what == msg.what) && (obj == null || obj == msg.obj) && (r == null || r == msg.callback)
            if (shouldRemoved) {
                msg.recycle()
            }
            shouldRemoved
        }
    }


    fun hasMessages(what: Int): Boolean {
        queue.forEach {
            if (it.what == what) {
                return true
            }
        }
        return false
    }

    fun quit(safe: Boolean) {
        if (!quitAllowed) {
            throw IllegalArgumentException("Main thread not allowed to quit.")
        }
        mQuiting = true
        println("Quit, message queue size = ${queue.size}")
        if (!safe) {
            queue.clear()
        }
        queue.offer(POSITION.apply {
            `when` = System.currentTimeMillis()
            who = "KILLED"
        })

    }
}