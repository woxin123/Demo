package top.mcwebsite.kotlin.demo.coroutine.base

import kotlin.coroutines.RestrictsSuspension

@RestrictsSuspension
class RestricProduceScope<T> {
    suspend fun produce(value: T) {

    }
}

fun callLaunchCoroutineRestricted() {
    launchCoroutine(RestricProduceScope<Int>()) {
        println("In Coroutine.")
        produce(1024)
        // delay(1000) // 错误不能调用外部的挂起函数
        produce(2048)
    }
}