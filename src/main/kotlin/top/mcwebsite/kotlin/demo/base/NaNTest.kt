package top.mcwebsite.kotlin.demo.base

val realCurrentZoom = 1F

fun main() {
    println("Hello World!")
    val aa:Float = (-2.0 / 0.0).toFloat()
    println(aa)
    val a: Float = (0.0 / 0.0).toFloat()
    if (a > 100F) {
        println("true")
    }
    println(realCurrentZoom * a)
    if (realCurrentZoom * a >= 5.0) {
        return
    }
    println("NaN")
    println(a)
}