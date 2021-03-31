package top.mcwebsite.kotlin.demo.coroutine.channel

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
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

suspend fun suspendOnSending() {
    val channel = Channel<Int>()
    val producer = GlobalScope.launch {
        var i = 0
        while (true) {
            delay(100)
            i++
            println("before send $i")
            channel.send(i)
            println("after send $i")
        }
    }
    val consumer = GlobalScope.launch {
        while (true) {
            delay(2000)
            val element = channel.receive()
            println("receive $element")
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

suspend fun iterateChannelWithWhile() {
    val channel = Channel<Int>()
    val producer = GlobalScope.launch {
        for (i in 0..3) {
            println("sending $i")
            channel.send(i)
            println("send $i")
        }
        channel.close()
    }

    val consumer = GlobalScope.launch {
        val iterator = channel.iterator()
        while (iterator.hasNext()) {
            val element = iterator.next()
            println(element)
            delay(2000)
        }
    }

    producer.join()
    consumer.join()
}

suspend fun iterateChannelWithForLoop() {
    val channel = Channel<Int>()

    val producer = GlobalScope.launch {
        for (i in 0..3) {
            println("sending $i")
            channel.send(i)
            println("send $i")
        }
        channel.close()
    }

    val consumer = GlobalScope.launch {
        for (element in  channel) {
            println(element)
            delay(2000)
        }
    }
    producer.join()
    consumer.join()
}

suspend fun main() {
//    sequences()
//    suspendOnSending()
//    iterateChannelWithWhile()
    iterateChannelWithForLoop()
}