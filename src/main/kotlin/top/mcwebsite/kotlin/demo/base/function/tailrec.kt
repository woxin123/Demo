package top.mcwebsite.kotlin.demo.base.function

import sun.jvm.hotspot.debugger.LongHashMap

private object Case1 {
    fun factorial(n: Long): Long {
        if (n == 1L) return 1
        return n * factorial(n - 1)
    }
}

private object Case2 {
    fun factorial(n: Int): Int {
        var total = 1
        var current = n
        while (current != 1) {
            val temp = current - 1
            total *= current
            current = temp
        }
        return total
    }
}

private object Case3 {
    tailrec fun factorial(total: Long, n: Long): Long {
        if (n == 1L) {
            return total
        }
        return factorial(n * total, n - 1)
    }

}

fun main() {
    val start = System.currentTimeMillis()
    val res = Case1.factorial(20)
    println("cost = ${System.currentTimeMillis() - start}")
    println(res)
}
