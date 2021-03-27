package top.mcwebsite.kotlin.demo.base.function

import java.lang.IllegalArgumentException

class User(val id: Int, val name: String, val address: String)

//fun saveUser(user: User) {
//    if (user.name.isEmpty()) {
//        throw IllegalArgumentException("Can't save user ${user.id}: empty Name")
//    }
//    if (user.address.isEmpty()) {
//        throw IllegalArgumentException("Can't save user ${user.id}: empty Name")
//    }
//    // 保存到数据库
//}

//fun saveUser(user: User) {
//    // 提取局部函数
//    fun validate(
//        user: User,
//        value: String,
//        fileName: String
//    ) {
//        if (value.isEmpty()) {
//            throw IllegalArgumentException("Can't save user ${user.id}: empty ${fileName}")
//        }
//    }
//    validate(user, user.name, "Name")
//    validate(user, user.address, "Address")
//    // 保存到数据库
//}

//fun saveUser(user: User) {
//    // 提取局部函数
//    // 局部函数可以访问所在函数中的所有参数和变量
//    fun validate(
//        value: String,
//        fileName: String
//    ) {
//        if (value.isEmpty()) {
//            throw IllegalArgumentException("Can't save user ${user.id}: empty ${fileName}")
//        }
//    }
//    validate(user.name, "Name")
//    validate(user.address, "Address")
//    // 保存到数据库
//}

// 使用扩展函数
fun User.validateBeforeSave() {
    fun validate(value: String, filedName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException("Can not save user $id: empty $filedName")
        }
    }
    validate(name, "Name")
    validate(address, "address")
}

fun saveUser(user: User) {
    user.validateBeforeSave()
    // 保存 user 到数据库
}

fun main() {

}