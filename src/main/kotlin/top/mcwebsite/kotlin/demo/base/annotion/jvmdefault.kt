package top.mcwebsite.kotlin.demo.base.annotion

interface Car {
    /**
     * 如果要使用 @JvmDefault 就必须在 gradle 中设置 -jvm-target=1.8 -Xjvm-default=enable
     */
    @JvmDefault
    fun run() = println("car is running")
}

class BMW : Car

fun main() {
    BMW().run()
}