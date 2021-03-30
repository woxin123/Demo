package top.mcwebsite.kotlin.demo.coroutine.channel

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

suspend fun sendAndReceiveBroadcast() {
    val broadcastChannel = BroadcastChannel<Int>(Channel.BUFFERED)
    val producer = GlobalScope.launch {
        List(3) {
            delay(100)
            broadcastChannel.send(it)
        }
    }
    List(3) { index ->
        GlobalScope.launch {
            val receiveChannel = broadcastChannel.openSubscription()
            for (i in receiveChannel) {
                println("#${index} received: $i")
            }
        }
    }.joinAll()
}

suspend fun main() {
    sendAndReceiveBroadcast()
}