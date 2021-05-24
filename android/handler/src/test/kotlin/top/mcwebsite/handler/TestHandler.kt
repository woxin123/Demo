package top.mcwebsite.handler

import org.junit.Before
import org.junit.Test
import kotlin.concurrent.thread

class TestHandler {

    private var looper: Looper? = null

    @Before
    fun testBefore() {
        thread(
            start = true,
            isDaemon = true,
            name = "HandlerThread"
        ) {
            Looper.prepare()
            looper = Looper.myLooper()
            Looper.loop()
        }
    }

    @Test
    fun testHandler() {
        while (looper == null) {
            Thread.yield()
        }
        val handler = Handler(
            looper = looper!!,
            callback = object : Handler.Callback {
                override fun handleMessage(msg: Message): Boolean {
                    when (msg.what) {
                        1 -> {
                            println("Hello World!")
                            println(Thread.currentThread().name)
                        }
                    }
                    return true
                }
            }
        )

        handler.sendMessage(handler.obtainMessage(what = 1))
        handler.post {
            println("Hello Handler")
        }

    }
}
