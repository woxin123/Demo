package top.mcwebsite.coroutine.dispatcher

import top.mcwebsite.coroutine.dispatcher.ui.SwingDispatcher

object Dispatchers {
    val Default by lazy {
        DispatcherContext(DefaultDispatcher)
    }

    val Swing by lazy {
        DispatcherContext(SwingDispatcher)
    }
}