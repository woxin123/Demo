package top.mcwebsite.handler

open class HandlerThread(
    name: String,
    priority: Int = 0
) : Thread(name) {


    private var mLooper: Looper? = null

    private var mHandler: Handler? = null

    private val SYNCOBJ = Object()

    protected fun onLooperPrepared() {

    }

    override fun run() {
        Looper.prepare()
        synchronized(SYNCOBJ) {
            mLooper = Looper.myLooper()
            SYNCOBJ.notifyAll()
        }
        onLooperPrepared()
        Looper.loop()
    }

    fun getLooper(): Looper? {
        if (!isAlive) {
            return null
        }

        synchronized(SYNCOBJ) {
            while (isAlive && mLooper == null) {
                try {
                    SYNCOBJ.wait()
                } catch (e: InterruptedException) {
                }
            }
        }
        return mLooper!!
    }

    fun getThreadHandler(): Handler? {
        if (mHandler == null) {
            mHandler = getLooper()?.let { Handler(it) }
        }
        return mHandler
    }

    fun quit(): Boolean {
        val looper = getLooper() ?: return false
        looper.quit()
        return true
    }
}