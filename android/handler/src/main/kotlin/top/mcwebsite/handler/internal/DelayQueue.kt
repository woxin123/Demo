package top.mcwebsite.handler.internal

import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.BlockingQueue
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import kotlin.NoSuchElementException

class DelayQueue<E: Delayed>()
    : AbstractQueue<E>(), BlockingQueue<E> {


    private val lock = ReentrantLock()
    private val priorityQueue = PriorityQueue<E>()
    private val available = lock.newCondition()

    constructor(c: Collection<E>) : this() {
        this.priorityQueue.addAll(c)
    }

    private var leader: Thread? = null

    override fun iterator(): MutableIterator<E> {
        return Itr(toArray())
    }

    override fun toArray(): Array<Any> {
        val lock = this.lock
        lock.lock()
        try {
            return priorityQueue.toArray()
        } finally {
            lock.unlock()
        }
    }

   override fun add(e: E) = offer(e)

    override fun offer(e: E): Boolean {
        val lock = this.lock
        try {
            lock.lock()
            priorityQueue.offer(e)
            if (e == priorityQueue.peek()) {
                leader = null
                available.signal()
            }
            return true
        } finally {
            lock.unlock()
        }
    }


    override fun offer(e: E, timeout: Long, unit: TimeUnit): Boolean {
        TODO("Not yet implemented")
    }

    override fun poll(): E? {
        val lock = this.lock
        lock.lock()
        try {
            val first = priorityQueue.peek() ?: return null
            return if (first.getDelay(TimeUnit.NANOSECONDS) > 0) {
                null
            } else {
                priorityQueue.poll()
            }
        } finally {
            lock.unlock()
        }
    }

    override fun poll(timeout: Long, unit: TimeUnit): E? {
        var nanos = unit.toNanos(timeout)
        val lock = this.lock
        lock.lockInterruptibly()
        try {
            while (true) {
                var first = priorityQueue.peek()
                if (first == null) {
                    if (nanos <= 0) {
                        return null
                    } else {
                        nanos = available.awaitNanos(nanos)
                    }
                } else {
                    val delay = first.getDelay(TimeUnit.NANOSECONDS)
                    if (delay <= 0) {
                        return priorityQueue.poll()
                    }
                    if (nanos <= 0) {
                        return null
                    }
                    first = null // don't retain ref while waiting
                    if (nanos < delay || leader != null) {
                        nanos = available.awaitNanos(nanos)
                    } else {
                        val thisThread = Thread.currentThread()
                        leader = thisThread
                        try {
                            val timeLeft = available.awaitNanos(delay)
                            nanos -= delay - timeLeft
                        } finally {
                            if (leader == thisThread) {
                                leader = null
                            }
                        }
                    }
                }
            }
        } finally {
            if (leader == null && priorityQueue.peek() != null) {
                available.signal()
            }
            lock.unlock()
        }
    }

    override fun peek(): E {
        val lock = this.lock
        lock.lock()
        try {
            return priorityQueue.peek()
        } finally {
            lock.unlock()
        }
    }

    override val size: Int
        get() {
            val lock = this.lock
            lock.lock()
            try {
                return priorityQueue.size
            } finally {
                lock.unlock()
            }
        }

    override fun put(e: E) {
        offer(e)
    }

    override fun take(): E {
        val lock = this.lock
        lock.lockInterruptibly()
        try {
            while (true) {
                var first = priorityQueue.peek()
                if (first == null) {
                    available.await()
                } else {
                    val delay = first.getDelay(TimeUnit.NANOSECONDS)
                    if (delay <= 0) {
                        return priorityQueue.poll()
                    }
                    first = null // don't retain ref while waiting
                    if (leader != null) {
                        available.await()
                    } else {
                        val thisThread = Thread.currentThread()
                        leader = thisThread
                        try {
                            available.awaitNanos(delay)
                        } finally {
                            if (leader == thisThread) {
                                leader = null
                            }
                        }
                    }
                }
            }
        } finally {
            if (leader == null && priorityQueue.peek() != null) {
                available.signal()
            }
            lock.unlock()
        }
    }

    private fun peekExpired(): E? {
        val first = priorityQueue.peek()
        return if (first == null || first.getDelay(TimeUnit.NANOSECONDS) > 0) {
            null
        } else {
            first
        }
    }

    override fun remainingCapacity(): Int {
        return Int.MAX_VALUE
    }

    override fun drainTo(c: MutableCollection<in E>): Int {
        return drainTo(c, Int.MAX_VALUE)
    }

    override fun drainTo(c: MutableCollection<in E>, maxElements: Int): Int {
        if (c == this) {
            throw IllegalArgumentException()
        }
        val lock = this.lock
        try {
            var n = 0
            var e: E? = null
            while (n < maxElements && peekExpired().apply { e = this } != null) {
                c.add(e!!)
                priorityQueue.poll()
                ++n
            }
            return n
        } finally {
            lock.unlock()
        }
    }

    override fun clear() {
        val lock = this.lock
        lock.lock()
        try {
            priorityQueue.clear()
        } finally {
            lock.unlock()
        }
    }

    fun removeEQ(a: Any) {
        val lock = this.lock
        try {
            val iterator = priorityQueue.iterator()
            while (iterator.hasNext()) {
                if (a == iterator.next()) {
                    iterator.remove()
                    break
                }
            }
        } finally {
            lock.unlock()
        }
    }

    private inner class Itr(
        private val array: Array<Any>
    ) : MutableIterator<E> {

        var cursor = 0
        var lastRet = -1

        override fun hasNext(): Boolean {
            return cursor < array.size
        }

        override fun next(): E {
            if (cursor >= array.size) {
                throw NoSuchElementException()
            }
            lastRet = cursor
            return array[cursor++] as E
        }

        override fun remove() {
            if (lastRet < 0) {
                throw IllegalStateException()
            }
            removeEQ(array[lastRet])
            lastRet = -1
        }

    }

}