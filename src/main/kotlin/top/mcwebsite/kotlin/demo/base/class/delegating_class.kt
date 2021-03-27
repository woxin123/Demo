package top.mcwebsite.kotlin.demo.base.`class`

class DelegatingCollection<T>(
    innerList: Collection<T> = ArrayList<T>()
) : Collection<T> by innerList

class CountingSet<T>(
    val innerList: MutableCollection<T> = HashSet<T>()
) : MutableCollection<T> by innerList {

    var objectsAdded = 0

    override fun add(element: T): Boolean {
        objectsAdded++
        return innerList.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        objectsAdded += elements.size
        return innerList.addAll(elements)
    }
}

fun main() {
    val cset = CountingSet<Int>()
    cset.addAll(listOf(1, 2, 3))
    println("${cset.objectsAdded} objects were added, ${cset.size} remain")
}