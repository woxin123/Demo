package top.mcwebsite.kotlin.demo.coroutine

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.ArithmeticException
import kotlin.coroutines.CoroutineContext

// 定义一个全局的协程异常处理器
// 然后需要将这个类注册为 SPI
// 也就需要在 resource 目录下定义一个 kotlinx.coroutines.CoroutineExceptionHandler 文件，文件的内容为这个类的全限定类名
class GlobalCoroutineExceptionHandler : CoroutineExceptionHandler {
    override val key = CoroutineExceptionHandler

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        println("Global Coroutine exception: $exception")
    }
}


// 测试一下
fun testGlobalCoroutineException() {
    GlobalScope.launch {
        throw ArithmeticException("Hey!")
    }
    Thread.sleep(10000)
}