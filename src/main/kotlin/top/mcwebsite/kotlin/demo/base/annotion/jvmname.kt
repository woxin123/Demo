package top.mcwebsite.kotlin.demo.base.annotion

class Student {
    @get:JvmName("getStudentName")
    @set:JvmName("setStudentName")
    var name: String = "张三"

    @JvmName("getStudentScore")
    fun getScore(): Double {
        return 100.0
    }
}
