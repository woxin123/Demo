package top.mcwebsite.handler

import top.mcwebsite.handler.internal.Delayed
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.TimeUnit

class Message : Delayed {

    companion object {

        const val FLAG_IN_USE = 1 shl 0
        const val FLAGS_TO_CLEAR_ON_COPY_FROM = FLAG_IN_USE


        const val MAX_POOL_SIZE = 50

        val pollSync = Any()
        private var poll: LinkedList<Message> = LinkedList()
        private var pollSize = 0

        private var checkRecycle = true

        fun obtain(): Message {
            synchronized(pollSync) {
               if (!poll.isEmpty()) {
                   val res = poll.removeFirst()
                   res.flags = 0
                   pollSize--
                   return res
               }
            }
            return Message()
        }

        fun obtain(orig: Message): Message {
            val m = obtain()
            m.what = orig.what
            m.args1 = orig.args1
            m.args2 = orig.args2
            m.obj = orig.obj
            m.target = orig.target
            m.callback = orig.callback
            return m
        }


        fun obtain(h: Handler, what: Int? = null, callback: Runnable? = null, obj: Any? = null): Message {
            val m = obtain()
            m.target = h
            if (what != null) {
                m.what = what
            }
            m.obj = obj
            m.callback = callback
            return m
        }

    }

    var what = 0
    var args1 = 0
    var args2 = 0
    var obj: Any? = null
    var `when`: Long = 0
    var flags = 0
    var who: String? = null
    var target: Handler? = null
    var callback: Runnable? = null

    fun isInUse(): Boolean {
        return (flags and FLAG_IN_USE) == FLAG_IN_USE
    }

    fun markInUse() {
        flags = flags or FLAG_IN_USE
    }

    fun recycle() {
        if (isInUse()) {
            if (checkRecycle) {
                throw IllegalStateException("This message cannot be recycled because it is still in use.")
            }
            return
        }
        recycleUnchecked()
    }

    fun recycleUnchecked() {
        flags = FLAG_IN_USE
        what = 0
        args1 = 0
        args2 = 0
        obj = null
        `when` = 0
        target = null
        callback = null

        synchronized(pollSync) {
            if (pollSize < MAX_POOL_SIZE) {
                poll.add(this)
                pollSize++
            }
        }
    }

    fun copyFrom(msg: Message) {
        this.flags = msg.flags and FLAGS_TO_CLEAR_ON_COPY_FROM.inv()
        this.what = msg.what
        this.args1 = msg.args1
        this.args2 = msg.args2
        this.obj = msg.obj
    }


    override fun getDelay(timeUnit: TimeUnit): Long {
        return timeUnit.convert(`when` - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
    }

    override fun compareTo(other: Delayed): Int {
        return if (other is Message) {
            (this.`when` - other.`when`).toInt()
        } else {
            (getDelay(TimeUnit.MILLISECONDS) - other.getDelay(TimeUnit.MILLISECONDS)).toInt()
        }
    }


}