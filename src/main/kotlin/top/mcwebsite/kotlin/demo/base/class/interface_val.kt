package top.mcwebsite.kotlin.demo.base.`class`

interface User {
    val nickname: String
}

class PrivateUser(override val nickname: String) : User

class SubscribingUser(val email: String): User {
    override val nickname: String
        get() = email.substringBefore('@')
}

class FacebookUser(val accountId: Int) : User {
    override val nickname = getFacebookName(accountId)

    fun getFacebookName(accountId: Int): String {
        return ""
    }
}
