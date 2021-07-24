package top.mcwebsite.okhttpdemo

import okhttp3.OkHttpClient
import okhttp3.Request

class OKHttpGetDemo {
    val client = OkHttpClient();

    fun run(url: String): String? {
        val request = Request.Builder()
            .url(url)
            .build()
        return client.newCall(request).execute().body?.use {
            it.string()
        }
    }
}

fun main() {
    val example = OKHttpGetDemo()
    val response = example.run("https://rwa.github.com/square/okhttp/master/README.md")
    println(response)
}
