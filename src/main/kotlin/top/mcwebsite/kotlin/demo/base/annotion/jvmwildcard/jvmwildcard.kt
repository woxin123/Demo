package top.mcwebsite.kotlin.demo.base.annotion.jvmwildcard

val numberList: List<Number> = ArrayList<Int>()

interface IConvert {
    fun convertData(data: List<@JvmSuppressWildcards(suppress = false) String>)

    fun getData(): List<@JvmWildcard String>
}

fun transformList(list: List<@JvmSuppressWildcards Number>) : List<@JvmWildcard Number> {
    return list
}