package top.mcwebsite.java.demo.base.clazz

fun interface IntPredicate {
    fun accept(i: Int): Boolean
}


// Create an instance of class
//    val isEven = object : IntPredicate {
//        override fun accept(i: Int): Boolean {
//            return i % 2 == 0
//        }
//    }
// Create an instance using lambda
val isEven = IntPredicate { it % 2 == 0 }

fun main() {
    println("7 is even? - ${isEven.accept(7)}")
}