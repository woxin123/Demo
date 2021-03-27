package top.mcwebsite.kotlin.demo.base.`class`

interface Clickable {
    fun click()
    // 接口中实现的方法
    fun showOff() = println("I'm clickable")
}

interface Focusable {
    fun setFocus(b: Boolean) =
        println("I ${if (b) "got" else "lost"} focus.")
    fun showOff() = println("I'm focusable!")
}

class Button : Clickable, Focusable {
    override fun click() {
        println("I was clickable")
    }

    /**
     * 如果继承的接口或者类中有两个相同切且已经实现的方法， Kotlin 会强制要求重写这个方法
     */
    override fun showOff() {
        super<Clickable>.showOff()
        super<Clickable>.showOff()
    }

}

fun main() {
    val button = Button()
    button.showOff()
    button.setFocus(true)
    button.click()
}