package top.mcwebsite.kotlin.demo.base.annotion

class Person {
    @JvmField   // 无法在使用 set(value) 去设置值了
    var age = 18
}

fun main() {
    Person()
}