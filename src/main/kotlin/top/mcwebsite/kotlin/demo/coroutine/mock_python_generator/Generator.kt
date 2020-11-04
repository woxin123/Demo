package top.mcwebsite.kotlin.demo.coroutine.mock_python_generator

import java.lang.IllegalStateException
import java.lang.IndexOutOfBoundsException
import kotlin.coroutines.*

interface Generator<T> {
    operator fun iterator(): Iterator<T>
}

class GeneratorImpl<T>(
    private val block: suspend GeneratorScope<T>.(T) -> Unit,
    private val parameter: T
) : Generator<T> {
    override fun iterator(): Iterator<T> {
        return GeneratorIterator(block, parameter)
    }
}

sealed class State {
    class NotReady(val continuation: Continuation<Unit>): State()
    class Ready<T>(val continuation: Continuation<Unit>, val nextValue: T) : State()

    object Done: State()
}

class GeneratorIterator<T>(
    private val block: suspend GeneratorScope<T>.(T) -> Unit,
    private val parameter: T
) : GeneratorScope<T>, Iterator<T>, Continuation<Any?> {

    override val context: CoroutineContext = EmptyCoroutineContext

    private var state: State

    init {
        val coroutineBlock: suspend GeneratorScope<T>.() -> Unit = {
            block(parameter)
        }
        val start = coroutineBlock.createCoroutine(this, this)
        state = State.NotReady(start)
    }

    /**
     * 挂起当前协程，如果当前的状态是 NotReady，然后将状态转换为 Ready，并将值封装进入 Ready 中
     */
    override suspend fun yield(value: T) =
        suspendCoroutine<Unit> { continuation ->
            state = when (state) {
                is State.NotReady -> State.Ready(continuation, value)
                is State.Ready<*> -> throw IllegalStateException("Cannot yield a value while ready.")
                State.Done -> throw IllegalStateException("Cannot yield a value while done.")
            }
        }

    private fun resume() {
        when (val currentState = state) {
            is State.NotReady -> currentState.continuation.resume(Unit)
        }
    }

    /**
     * 每次首先调用 hasNext()
     * 如果最终的结果是 NotReady，则调用 continuation 的 resume 方法，然后会调用 resume，然后会调用
     * yield 函数
     */
    override fun hasNext(): Boolean {
        resume()
        return state != State.Done
    }

    /**
     * 获取 next
     * 如果当前的值是 NotReady，那么调用 resume() 转换状态，然后再次调用 next()
     * 如果是 Ready，那么将状态转换为 NotReady，然后从当前状态中取出值
     */
    override fun next(): T {
        return when (val currentState = state) {
            is State.NotReady -> {
                resume()
                return next()
            }
            is State.Ready<*> -> {
                state = State.NotReady(currentState.continuation)
                (currentState as State.Ready<T>).nextValue
            }
            State.Done -> throw IndexOutOfBoundsException("No value left.")
        }
    }

    /**
     * 执行结束，将状态转换为 Done
     */
    override fun resumeWith(result: Result<Any?>) {
        state = State.Done
        result.getOrThrow()
    }

}

interface GeneratorScope<T> {
    suspend fun yield(value: T)
}

fun <T> generator(block: suspend GeneratorScope<T>.(T) -> Unit): (T) -> Generator<T> {
    /**
     * 可以看到这里的返回值很奇怪
     * 这个函数的返回值是 (T) -> Generator<T>
     * 所以这里最终返回的是一个匿名的函数 (T) -> Generator<T>
     */
    return { parameter ->
        GeneratorImpl(block, parameter)
    }
}

fun main() {
    // 这里的这个 nums 的类型是一个 (Int) -> Generator<Int>
    val nums: (Int) -> Generator<Int> = generator {  start: Int ->
        for (i in 0..5) {
            yield(start + i)
        }
    }
    // 这里调用 nums 函数，返回一个 Generator<Int> 类型的对象
    val seq: Generator<Int> = nums(10)

    for (j in seq) {
        println(j)
    }

    val sequence = sequence {
        yield(1)
        yield(2)
        yield(3)
        yield(4)
        yieldAll(listOf(1, 2, 3, 4))
    }

    for (element in sequence) {
        println(element)
    }

    val fibonacci = sequence {
        yield(1L)
        var current = 1L
        var next = 1L
        while (true) {
            yield(next)
            next += current
            current = next - current
        }
    }
    fibonacci.take(10).forEach(::println)
}