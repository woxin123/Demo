package top.mcwebsite.kotlin.demo.coroutine.channel

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

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

suspend fun producer() {
    val receiveChannel: ReceiveChannel<Int> = GlobalScope.produce {
        repeat(100) {
            delay(1000)
            send(it)
        }
    }

    val consumer = GlobalScope.launch {
        for (i in receiveChannel) {
            println("received: $i")
        }
    }

    consumer.join()
}

suspend fun consumer() {
    val sendChannel: SendChannel<Int> = GlobalScope.actor {
        while (true) {
            val element = receive()
            println(element)
        }
    }

    val producer = GlobalScope.launch {
        for (i in 0..3) {
            sendChannel.send(i)
        }
    }
    producer.join()
}

suspend fun closeChannel() {
    val channel = Channel<Int>(3)

    val producer = GlobalScope.launch {
        List(3) {
            channel.send(it)
            println("send $it")
        }
        channel.close()
        println(
            """close channel.
        |  - ClosedForSend: ${channel.isClosedForSend}
        |  - ClosedForReceive: ${channel.isClosedForReceive}""".trimMargin())
    }
    val consumer = GlobalScope.launch {
        for (element in channel) {
            println("receive $element")
        }
        delay(1000)

        println("""After Consuming.
            |  - ClosedForSend: ${channel.isClosedForSend}
            |  - ClosedForReceive: ${channel.isClosedForReceive}
        """.trimMargin())
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

suspend fun sequencesWithChannelAndDispatcher() {
    val channel = GlobalScope.produce(Dispatchers.Unconfined) {
        println("A")
        send(1)
        withContext(Dispatchers.IO) {
            println("B")
            send(2)
        }
        println("Done")
    }

    for (item in channel) {
        println("Got $item")
    }
}
suspend fun main() {
//    sequences()
//    suspendOnSending()
//    iterateChannelWithWhile()
//    iterateChannelWithForLoop()
//    producer()
//    consumer()
//    closeChannel()
    sequencesWithChannelAndDispatcher()
}