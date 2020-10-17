package top.mcwebsite.java.demo.base.annotion

import com.sun.xml.internal.fastinfoset.util.StringArray

/**
 * 不同于 Java 的 @Deprecated 注解，Kotlin 为我们提供了 replaceWith 属性，能够方便的帮助我们进行一键替换
 * ReplaceWith 有两个属性，一个是 expression，可以写一个替换后的表达式，第二个参数是一个变长参数 import，替换后需要导入的包
 *
 * 另外还提供了一个 level 参数，表示这个过时 API 的级别，有三个级别：
 * WRANING      默认为 wraning   使用是会有警告
 * ERROR        使用时将会编译错误
 * HIDDEN       被标记的将会被 IDE 忽略，编译时将引用不到
 *
 */

@Deprecated("Use removeAt(index) instead.", replaceWith = ReplaceWith("removeAt(index)"), level = DeprecationLevel.WARNING)
fun remove(index: Int) {
    println("index = $index")
}

fun removeAt(index: Int) {
    println("new removeAt index: $index")
}

@Target(AnnotationTarget.CLASS, AnnotationTarget.CONSTRUCTOR)
annotation class MyAnnotation(val strs: Array<String>)

fun main() {
    // 如果我们调用 remove 方法，IDEA 将会提示我们将其修改为 removeAt，并且如果添加了 replaceWith 属性，将提供一键替换功能
    remove(123)
}
