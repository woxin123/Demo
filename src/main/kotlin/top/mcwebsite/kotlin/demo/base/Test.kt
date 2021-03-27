package top.mcwebsite.kotlin.demo.base

import java.util.*


fun main() {
    var bei: Double = 1.0
    var next: Double = 0.0
    var current: Double = 0.0
    var number: Double = 1.0
    var change: Double = 1.0
    val max: Double = 100.0
    val random = Random()
    while (next < max) {
        change = Math.random() + 1
//        if (random.nextBoolean()) {
//            change = -change
//        }
        println("chang = $change")
        if (current + change > max) {
            next = max
            change = max - current
        } else {
            next = current + change
        }
        bei = 1 +  (change / number).toDouble()
        current = next
        number *= bei
        println(number)
        println("bei = $bei next = $next")
    }
    println("${1F.plus(0.025F.div(1F.plus(0.025F)))}")
}