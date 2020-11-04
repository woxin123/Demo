package top.mcwebsite.kotlin.demo.coroutine.mock_lua

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.*


sealed class Status {
    // 表示协程创建之后不会立即执行，而需要等待 resume 函数的调用
    class Created(val continuation: Continuation<Unit>) : Status()
    // 表示协程内部调用 yield 函数之后挂起。泛型参数 P 表示协程的参数类型，该类型并非 yield 函数的参数类型 (resume 函数的返回值类型)
    class Yielded<P>(val continuation: Continuation<P>) : Status()
    // 表示协程外部调用 resume 函数之后协程继续执行。泛型参数 R 表示协程的返回值
    class Resumed<R>(val continuation: Continuation<R>) : Status()
    // 表示协程已经执行完毕
    object Dead : Status()
}

interface CoroutineScope<P, R> {
    // 协程启动的时候的参数
    val parameter: P?

    suspend fun yield(value: R): P
}

class Coroutine<P, R>(
    override val context: CoroutineContext,
    private val block: suspend CoroutineScope<P, R>.(P) -> R
) : Continuation<R> {

    companion object {
        fun <P, R> create(
            context: CoroutineContext = EmptyCoroutineContext,
            block: suspend CoroutineScope<P, R>.(P) -> R
        ): Coroutine<P, R> {
            return Coroutine(context, block)
        }
    }

    // 创建一个匿名的 Scope 类型的对象
    private val scope = object : CoroutineScope<P, R> {

        override var parameter: P? = null

        override suspend fun yield(value: R): P = suspendCoroutine { continuation ->
            // 更新当前 status 的状态
            // 如果当前的状态是 Resumed，那么将当前的状态转换成 Yielded 的状态
            // previousStatus 指的是更新前的状态，这里的 previousStatus 只能是 Resumed
            val previousStatus = status.getAndUpdate {
                when (it) {
                    is Status.Created -> throw IllegalStateException("Never started!")
                    is Status.Yielded<*> -> throw IllegalStateException("Already yielded!")
                    is Status.Resumed<*> -> Status.Yielded(continuation)
                    Status.Dead -> throw IllegalStateException("Already dead!")
                }
            }
            // 调用 Resumed 中的 continuation 的 resume
            (previousStatus as? Status.Resumed<R>)?.continuation?.resume(value)
        }
    }

    // 使用 AtomicReference 保证在多线程情况下也能正常运行
    private val status: AtomicReference<Status>

    val isActive: Boolean
        get() = status.get() != Status.Dead

    init {
        // coroutineBlock 是即将执行的协程体
        val coroutineBlock: suspend CoroutineScope<P, R>.() -> R = { block(parameter!!) }
        // 这里创建了一个协程，作用域是 scope，协程执行完成的回调是当前对象，也就是下面的 resumeWith 方法
        val start = coroutineBlock.createCoroutine(scope, this)
        // 初始化 status 为 Created 状态
        status = AtomicReference(Status.Created(start))
    }

    override fun resumeWith(result: Result<R>) {
        // 如果当前的状态是 Resumed 将转换为 Dead 表示当亲的协程已经结束了
        val previousStatus = status.getAndUpdate {
            when (it) {
                is Status.Created -> throw IllegalStateException("Never started!")
                is Status.Yielded<*> -> throw IllegalStateException("Already yielded!")
                is Status.Resumed<*> -> {
                    Status.Dead
                }
                Status.Dead -> throw IllegalStateException("Already dead!")
            }
        }
        (previousStatus as? Status.Resumed<R>)?.continuation?.resumeWith(result)
    }

    suspend fun resume(value: P): R = suspendCoroutine { continuation: Continuation<R> ->
        val previousStatus = status.getAndUpdate {
            when (it) {
                // 如果是 Created 转换为 Resumed
                is Status.Created -> {
                    scope.parameter = value
                    Status.Resumed(continuation)
                }
                // 如果是 Yielded 转换为 Resumed
                is Status.Yielded<*> -> {
                    Status.Resumed(continuation)
                }
                is Status.Resumed<*> -> throw IllegalStateException("Already resumed!")
                Status.Dead -> throw IllegalStateException("Already dead!")
            }

        }
        when (previousStatus) {
            // 如果之前的状态是 Create，则调用 resume，这里的调用是为了开启协程
            is Status.Created -> previousStatus.continuation.resume(Unit)
            // 如果前一个状态是 Yielded 也调用 resume
            is Status.Yielded<*> -> (previousStatus as Status.Yielded<P>).continuation.resume(value)
        }
    }

    suspend fun <SymT> SymCoroutine<SymT>.yield(value: R): P {
        return scope.yield(value)
    }
}

class Dispatcher: ContinuationInterceptor {
    override val key = ContinuationInterceptor

    private val executor = Executors.newSingleThreadExecutor()

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return DispatcherContinuation(continuation, executor)
    }
}

class DispatcherContinuation<T>(val continuation: Continuation<T>, val executor: Executor): Continuation<T> by continuation {

    override fun resumeWith(result: Result<T>) {
       executor.execute {
           continuation.resumeWith(result)
       }
    }
}

suspend fun main() {
    val produce = Coroutine.create<Unit, Int>(Dispatcher()) {
        for (i in 0..3) {
            println("send $i")
            yield(i)
        }
        200
    }

    val consumer = Coroutine.create<Int, Unit>(Dispatcher()) { param: Int ->
        println("start $param")
        for (i in 0..3) {
            val value = yield(Unit)
            println("receive $value")
        }
    }

    while (produce.isActive && consumer.isActive) {
        val result: Int = produce.resume(Unit)
        consumer.resume(result)
    }
}
