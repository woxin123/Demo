package top.mcwebsite.demo.current.guava.future

import com.google.common.util.concurrent.*
import java.util.concurrent.*

private val threadFactory = ThreadFactoryBuilder().setDaemon(true).setNameFormat("test-pool-%d").build()
private val executor = ThreadPoolExecutor(0, Int.MAX_VALUE,
    60L, TimeUnit.SECONDS, SynchronousQueue(false), threadFactory)

private val listenableExecutor = MoreExecutors.listeningDecorator(executor)


fun main() {
    for (i in 0..9) {
        val booleanTask: ListenableFuture<Boolean> = listenableExecutor.submit(Callable {
            try {
                Thread.sleep(1000L)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            true
        })
        try {
            // 使用 get 方法会导致当前线程一直等待直到 booleanTask 异步任务执行完毕
            println(booleanTask.get())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }

        Futures.addCallback(booleanTask, object : FutureCallback<Boolean> {
            override fun onSuccess(result: Boolean?) {
                println("BooleanTask: $result")
            }

            override fun onFailure(t: Throwable) {

            }
        }, executor)

        val stringTask = listenableExecutor.submit(Callable {
            "Hello World!"
        })

        Futures.addCallback(stringTask, object : FutureCallback<String> {
            override fun onSuccess(result: String) {
                println(result)
            }

            override fun onFailure(t: Throwable) {
            }
        }, listenableExecutor)
    }
}