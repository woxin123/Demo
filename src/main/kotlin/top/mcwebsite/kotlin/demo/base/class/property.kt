package top.mcwebsite.kotlin.demo.base.`class`

fun main() {
    val user = UserA("Alice")
    user.address = "Elsenheimerstrase 47, 80687 Muenchen"
}

class UserA(val name: String) {
    var address: String = "unspecfied"
        set(value: String) {
            println("""
                Address was changed for $name:
                "$field" -> "$value".""".trimIndent())
            field = value
        }
}

class LengthCounter {
    // 设置私有的 set 方法
    var counter: Int = 0
        private set

    fun addWord(word: String) {
        counter += word.length;
    }
}
