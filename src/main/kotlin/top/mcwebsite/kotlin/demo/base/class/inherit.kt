package top.mcwebsite.kotlin.demo.base.`class`

/**
 * Kotlin 中需要将能够被子类继承的或者重写的方法使用 open 修饰
 * 否则子类将不能继承这个类
 * 不能重写没有被 open 修饰的方法
 */
open class RichButton : Clickable {

    /**
     * 如果想要子类禁止重写继承于基类的方法可以使用 final 修饰这个方法
     */
    final override fun click() {}

    open fun disable() {}

    open fun animate() {}
}