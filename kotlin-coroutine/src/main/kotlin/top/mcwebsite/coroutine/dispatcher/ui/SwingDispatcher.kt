package top.mcwebsite.coroutine.dispatcher.ui

import top.mcwebsite.coroutine.dispatcher.Dispatcher
import javax.swing.SwingUtilities

object SwingDispatcher : Dispatcher {
    override fun dispatcher(block: () -> Unit) {
        SwingUtilities.invokeLater(block)
    }
}