package top.mcwebsite.kotlin.demo.base.function

fun main() {
    // 中缀函数调用
    // 1 to "one" 等价于 1.to("one")
    // 1 to "one" 调用的是
    // public infix fun <A, B> A.to(that: B): Pair<A, B> = Pair(this, that)
    val map = mapOf(1 to "one", 2 to "two", 53 to "fifty-three")
    println(map)

    // 这里首先 1 to "one" 返回一个 Pair 对象
    // 然后结构成变量 number 和 name
    val (number, name) = 1  to "one"

    val list = listOf(1, 2, 3, 4)
    // public data class IndexedValue<out T>(public val index: Int, public val value: T)
    for ((index, element) in list.withIndex()) {
        println("$index: $element")
    }
}