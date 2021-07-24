package top.mcwebsite.okhttpdemo

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion
import okhttp3.RequestBody.Companion.toRequestBody

class OKHttpPostDemo {

    companion object {
        val JSON = "application/json; charset=utf-8".toMediaType()
    }

    val client = OkHttpClient()

    fun post(url: String, json: String): String? {
        val body = json.toRequestBody(JSON)
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        return client.newCall(request).execute().body?.use {
            it.string()
        }
    }

    fun blowlingJson(player1: String, player2: String): String {
        return """
            { 
                'winCondition': 'HIGH_SCORE',
                'name': 'Bowling',
                'round': 4,
                'lastSaved': 1367702411696,
                'dateStarted': 1367702411696,
                'players': [
                    {
                        'name': $player1,
                        'history': [10, 8, 6, 7, 8],
                        'color': -13388315,
                        'total': 39
                    },
                    {
                        'name': $player2,
                        'history': [6, 10, 5, 10, 10],
                        'color': -48060,
                        'total': 41
                    }
                ]
            }
        """.trimIndent()
    }
}

fun main() {
    val example = OKHttpPostDemo()
    val json = example.blowlingJson("Jesse", "Jake")
    val response = example.post("http://www.roundsapp.com/post", json)
    println(response)
}