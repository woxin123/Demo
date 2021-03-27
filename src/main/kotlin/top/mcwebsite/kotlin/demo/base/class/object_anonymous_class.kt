package top.mcwebsite.kotlin.demo.base.`class`

import java.awt.event.MouseEvent
import java.awt.event.MouseListener


fun main() {
    val mouseListeners = mutableListOf<MouseListener>()
    mouseListeners.add(object : MouseListener {
        override fun mouseReleased(e: MouseEvent?) {
            // ...
        }

        override fun mouseEntered(e: MouseEvent?) {
            // ...
        }

        override fun mouseClicked(e: MouseEvent?) {
            // ...
        }

        override fun mouseExited(e: MouseEvent?) {
            // ...
        }

        override fun mousePressed(e: MouseEvent?) {
            // ...
        }
    })
}