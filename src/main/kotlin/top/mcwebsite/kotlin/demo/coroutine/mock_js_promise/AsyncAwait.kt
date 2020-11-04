package top.mcwebsite.kotlin.demo.coroutine.mock_js_promise

import android.os.Handler
import android.os.Looper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import top.mcwebsite.kotlin.demo.coroutine.common.api.githubApi
import top.mcwebsite.kotlin.demo.coroutine.common.dispatchers.DefaultDispatcher
import top.mcwebsite.kotlin.demo.coroutine.common.dispatchers.Dispatcher
import top.mcwebsite.kotlin.demo.coroutine.common.dispatchers.DispatcherContext
import java.lang.NullPointerException
import kotlin.concurrent.thread
import kotlin.coroutines.*

interface AsyncScope

suspend fun <T> AsyncScope.await(block: () -> Call<T>) = suspendCoroutine<T> { continuation ->
    println(Thread.currentThread().name)
    val call = block()
    call.enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            continuation.resumeWithException(t)
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                response.body()?.let(continuation::resume) ?: continuation.resumeWithException(NullPointerException())
            } else {
                continuation.resumeWithException(HttpException(response))
            }
        }
    })
}

fun async(context: CoroutineContext = EmptyCoroutineContext, block: suspend AsyncScope.() -> Unit) {
    val completion = AsyncCoroutine(context)
    block.startCoroutine(completion, completion)
}

/**
 * AsyncCoroutine 及时 Scope 也是执行完成调用的 Continuation
 */
class AsyncCoroutine(
    override val context: CoroutineContext = EmptyCoroutineContext
) : Continuation<Unit>, AsyncScope {
    override fun resumeWith(result: Result<Unit>) {
        result.getOrThrow()
    }
}

fun main() {
    // 可以通过 CoroutineInterceptor 切换线程
    val handlerDispatcher = DispatcherContext(DefaultDispatcher)

    async(handlerDispatcher) {
        val user = await { githubApi.getUserCallback("woxin123") }
        println(user)
    }
}