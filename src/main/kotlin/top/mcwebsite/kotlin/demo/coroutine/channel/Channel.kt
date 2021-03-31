package top.mcwebsite.kotlin.demo.coroutine.channel

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun useChannel() {
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

suspend fun sequences() {

    val sequence = sequence {
        println("A")
        yield(1)
        println("B")
        yield(2)
        println("Done")
    }

    println("before sequence")

    for (item in sequence) {
        println("Got $item")
    }
}

suspend fun main() {
    sequences()
}