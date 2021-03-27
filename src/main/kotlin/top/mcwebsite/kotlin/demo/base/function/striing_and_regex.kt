package top.mcwebsite.kotlin.demo.base.function

fun main() {
    // 使用正则表达式分割
    println("12.345-6.A".split("[.\\-]".toRegex()))
    // 指定多个分割符，不使用正则表达式
    println("12.345-6.A".split(".", "-"))
    parsePath("/User/yole/kotlin-book/chapter.adoc")
    parsePathWithRegex("/User/yole/kotlin-book/chapter.adoc")
}

fun parsePath(path: String) {
    val directory = path.substringBeforeLast("/")
    val fullName = path.substringAfterLast("/")
    val extension = fullName.substringAfterLast(".")
    println("Dir: $directory, name: $fullName, ext: $extension")
}

fun parsePathWithRegex(path: String) {
    val regex = """(.+)/(.+)\.(.+)""".toRegex()
    val matchResult = regex.matchEntire(path)
    if (matchResult != null) {
        val (directory, filename, extension) = matchResult.destructured
        println("Dir: $directory, name: $filename, ext: $extension")
    }
}