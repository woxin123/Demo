package top.mcwebsite.kotlin.demo.base.operatoring

data class MyPoint(
    val x: Int,
    val y: Int
) {
//    operator fun plus(other: MyPoint): MyPoint {
//        return MyPoint(x + other.x, y + other.y)
//    }
}

operator fun MyPoint.plus(point: MyPoint): MyPoint {
    return MyPoint(x + point.x, y + point.y)
}

operator fun MyPoint.times(scale: Double): MyPoint {
    return MyPoint((x * scale).toInt(), (y * scale).toInt())
}

fun main() {
    val p1 = MyPoint(10, 20)
    val p2 = MyPoint(30, 40)
    println(p1 + p2)
    println(p1 * 1.5)
}