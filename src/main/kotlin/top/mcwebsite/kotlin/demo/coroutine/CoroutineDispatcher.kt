package top.mcwebsite.kotlin.demo.coroutine

import kotlinx.coroutines.*
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

fun useGlobalScope() {
    GlobalScope.launch(Dispatchers.Default) {
        println("hello world!")
    }
}

// custom dispatcher
class MyDispatcher : CoroutineDispatcher() {

    companion object {
        private val EXECUTOR = Executors.newFixedThreadPool(10)
    }

    /**
     * @context CoroutineContext
     * @block task, need to run
     */
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        EXECUTOR.submit(block)
    }
}

// 可以将我们自己定义好的线程池转成调度器
// 使用扩展函数 asCoroutineDispatcher 就可以将 Executor 转换成调度器，不过这个调度器需要在使用完毕后主动关闭，以免造成线程泄漏。
// 在下面的例子中我们使用 use 在协程执行完成后主动关闭这个调度器
suspend fun transExecutorToDispatcher() {
    Executors.newSingleThreadExecutor()
        .asCoroutineDispatcher()
        .use { dispatcher ->
            val result = GlobalScope.async {
                delay(100)
                "Hello World!"
            }.await()
        }
}

// withContext，可以用来简化前面的例子
// withContext 会将参数中的 lambda 表达式调度到对应的调度器上，它自己本身就是一个挂起函数，返回值为 lambda 表达式的值，由此可见它的作用几乎等价与 async { ... }
suspend fun transExecutorToDispatcherUseWithContext() {
    Executors.newSingleThreadExecutor()
        .asCoroutineDispatcher()
        .use { dispatcher ->
            val result = withContext(dispatcher) {
                delay(100)
                "Hello World"
            }
            println(result)
        }
}

suspend fun main() {
    transExecutorToDispatcherUseWithContext()
}
