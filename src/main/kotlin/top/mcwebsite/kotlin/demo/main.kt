@file:JvmName("MainCls")
package top.mcwebsite.kotlin.demo

import top.mcwebsite.kotlin.demo.coroutine.testGlobalCoroutineException
import top.mcwebsite.kotlin.demo.coroutine.testTimeOutCancel
import top.mcwebsite.kotlin.demo.coroutine.testTimeoutUseWithTimeout
import top.mcwebsite.kotlin.demo.coroutine.useChannle

suspend fun main() {
//    testGlobalCoroutineException()
//    testTimeOutCancel()
//    testTimeoutUseWithTimeout()
    useChannle()

}

