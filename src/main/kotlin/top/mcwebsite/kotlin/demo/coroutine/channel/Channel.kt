package top.mcwebsite.kotlin.demo.coroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun useChannle() {
    val channel = Channel<Int>()
    val producer = GlobalScope.launch {
        var i = 0
        while (true) {
            delay(1000L)
            channel.send(i++)
        }
    }

    val consumer = GlobalScope.launch {
        while(true) {
            val element = channel.receive()
            println(element)
        }
    }

    producer.join()
    consumer.join()
}
