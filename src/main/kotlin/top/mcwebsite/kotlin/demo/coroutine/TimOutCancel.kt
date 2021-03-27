package top.mcwebsite.kotlin.demo.coroutine

import kotlinx.coroutines.*
import top.mcwebsite.kotlin.demo.base.function.list

// 设置超时取消
fun testTimeOutCancel() {
    GlobalScope.launch(Dispatchers.IO) {
        val userDeferred = async {
            getUserSuspend()
        }

        val timeoutJob = launch {
            delay(5000)
            userDeferred.cancel()
        }
        val user = userDeferred.await()
        timeoutJob.cancel()
        println(user)
    }
    Thread.currentThread().join()
}


suspend fun getUserSuspend(): User {
    var i = 0
    delay(8000)
    return User("张三", 19)
}

data class User(
    val name: String,
    val age: Int
)

// 使用 withTimeout 来设置超时
suspend fun testTimeoutUseWithTimeout() {
    GlobalScope.launch {
        val user = withTimeout(5000) {
            getUserSuspend()
        }
        println(user)
    }.join()
}

// 禁止取消
suspend fun forbidCancel() {
    GlobalScope.launch {
        val job = launch {
            listOf(1, 2, 3, 4).forEach {
                yield() // 取消的不一定是 yield，可能是 delay
                withContext(NonCancellable) {
                    delay((it * 100).toLong())
                }
            }
        }
        delay(200L)
        job.cancelAndJoin()
    }.join()
}