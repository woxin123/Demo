package top.mcwebsite.woxin.ktorsample.client

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*

data class GitUser(val login: String, val avatar_url: String, val location: String)

suspend fun main() {
    val client = HttpClient(OkHttp) {
        install(JsonFeature)
    }

    // Get the content of an URL.
    val user = client.get<GitUser>("https://api.github.com/users/woxin123")
    
    println(user)
    client.close()
}