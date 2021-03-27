package top.mcwebsite.kotlin.demo.common.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.Serializable

val githubApi by lazy {
    val retrofit = Retrofit.Builder()
        .client(OkHttpClient.Builder().addInterceptor {
            it.proceed(it.request()).apply {
                println("request: ${code()}")
            }
        }.build())
        .baseUrl("https://api.github.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    retrofit.create(GithubApi::class.java)
}

interface GithubApi {

    @GET("users/{login}")
    fun getUserCallback(@Path("login") login: String): Call<User>

    @GET("users/{login}")
    suspend fun getUserSuspend(@Path("login") login: String): User
}

data class User(
    val id: String,
    val name: String,
    val url: String
) : Serializable
