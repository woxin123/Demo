package top.mcwebsite.handler

import java.util.*

class Handler(
    val looper: Looper,
    private val callback: Callback? = null
) {

    companion object {
        private fun getPostMessage(r: Runnable, token: Any? = null): Message {
            val m = Message.obtain()
            m.obj = token
            m.callback = r
            return m
        }
    }

    interface Callback {
        fun handleMessage(msg: Message): Boolean
    }

    private val queue by lazy { looper.queue }


    fun dispatchMessage(msg: Message) {
        if (msg.callback != null) {
            msg.callback?.run()
        } else {
            if (callback?.handleMessage(msg) == true) {
                return
            }
        }
    }

    fun sendMessageAtTime(msg: Message, updateMillis: Long): Boolean {
        return enqueueMessage(queue, msg, updateMillis)
    }

    fun sendMessageDelayed(msg: Message, delayMillis: Long) {
        sendMessageAtTime(msg, System.currentTimeMillis() + if (delayMillis < 0) 0 else delayMillis)
    }

    fun sendMessage(msg: Message) {
        return sendMessageDelayed(msg, 0)
    }

    private fun enqueueMessage(queue: MessageQueue, msg: Message, updateMillis: Long): Boolean {
        msg.target = this

        return queue.enqueueMessage(msg, updateMillis)
    }

    fun removeMessages(what: Int, obj: Any? = null) {
        queue.removeMessages(this, what = what, obj = obj)
    }

    fun removeCallbacksAndMessage(token: Any?) {
        queue.removeMessages(this, obj = token)
    }

    fun hasMessages(what: Int): Boolean {
        return queue.hasMessages(what)
    }

    fun obtainMessage(what: Int? = null, obj: Any? = null, callback: Runnable? = null): Message {
        return Message.obtain(this, what = what, obj = obj, callback = callback)
    }


    fun post(r: Runnable) {
         sendMessageDelayed(getPostMessage(r), 0)
    }

    fun post(action: () -> Unit) {
        sendMessageDelayed(getPostMessage({ action.invoke() }), 0)
    }

    fun postAtTime(r: Runnable, updateMillis: Long) {
         sendMessageAtTime(getPostMessage(r), updateMillis)
    }



}