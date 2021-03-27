package top.mcwebsite.kotlin.demo.base.function

fun main(args: Array<String>) {
    // 这里需要通过 * 将数组转换成 vararg 类型的变量
    val list = listOf("args", *args)
    println(list)
}

/**
 * vararg 表示可变参数
 */
fun <T> listOf(vararg elements: T): List<T> {
    return ArrayList<T>().apply {
        elements.forEach { add(it) }
    }
}