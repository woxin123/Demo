package top.mcwebsite.kotlin.demo.base.`class`

import java.io.Serializable

interface State : Serializable

interface View {
    fun getCurrentState(): State
    fun restoreState(state: State) {}
}

class ButtonA : View {
    override fun getCurrentState(): State = ButtonState()

    override fun restoreState(state: State) {
    }

    /**
     * 相当于 Java 中的 static class
     * 不存储外部引用
     */
    class ButtonState : State {

    }
}

class Outer {
    inner class Inner {
        fun getOuterReference() = this@Outer
    }
}