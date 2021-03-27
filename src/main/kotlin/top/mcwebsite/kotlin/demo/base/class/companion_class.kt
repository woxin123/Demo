package top.mcwebsite.kotlin.demo.base.`class`

import com.google.gson.Gson

class A {
    companion object {
        fun bar() {
            println("Companion object called")
        }
    }
}

fun getFacebookName(faceBookAccountId: Int): String {
    return "1000"
}

class UserHaveCompanion(val nickname: String) {
    companion object {
        fun newSubscribeingUser(email: String) =
            UserHaveCompanion(email.substringBefore('@'))

        fun newFacebookUser(accountId: Int) =
            UserHaveCompanion(getFacebookName(accountId))
    }
}

/**
 * 声明一个命令的伴生对象
 */
class Person(val name: String) {

    companion object Loader {
        private val gson = Gson()

        fun fromJson(jsonText: String): Person =
            gson.fromJson<Person>(jsonText, Person::class.java)
    }
}

/**
 * 在伴生对象中实现接口
 */
interface JSONFactory<T> {
    fun fromJson(jsonText: String) : T
}

class Person2(val name: String) {
    companion object : JSONFactory<Person2> {

        private val gson = Gson()

        override fun fromJson(jsonText: String): Person2 =
            gson.fromJson(jsonText, Person2::class.java)
    }
}

fun <T> loadFromJSON(factory: JSONFactory<T>): T {
    return factory.fromJson("{name: 'Dmitry'}")
}

class Person3(val firstName: String, val lastName: String) {
    companion object {
    }
}

fun Person3.Companion.fromJSON(json: String): Person3 {
    val gson = Gson()
    return gson.fromJson(json, Person3::class.java)
}

fun main() {
    A.bar()

    val person = Person.Loader.fromJson("{name: 'Dmitry'}")
    println(person.name)

    val personAnother = Person.fromJson("{name: 'Brent'}")
    println(personAnother.name)

    val person3 = loadFromJSON(Person2)
    println(person3.name)
    val person33 = Person3.fromJSON("{name: 'Brent'}")
    println(person3.name)
}