package top.mcwebsite.kotlin.demo.base.annotion



fun main() {
   testList(listOf("1", "2"))
}

fun testList(list: List<*>) {
    @Suppress("UNCHECKED_CAST")
    val strings = list as List<String>
}