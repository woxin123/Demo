package top.mcwebsite.kotlin.demo.base.function

val String.lastChar: Char
    get() = get(this.length - 1)

var StringBuilder.lastChar: Char
    get() = get(length - 1)
    set(value: Char) {
        this.setCharAt(length - 1, value)
    }

