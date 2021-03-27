package top.mcwebsite.kotlin.demo.coroutine.select

import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.selects.select
import top.mcwebsite.kotlin.demo.common.api.User
import top.mcwebsite.kotlin.demo.common.api.githubApi
import java.io.File

val localDir = File("localCache").also { it.mkdirs() }

val gson = Gson()

fun CoroutineScope.getUserFromApi(login: String) = async(Dispatchers.IO) {
    githubApi.getUserSuspend(login)
}

fun CoroutineScope.getUserFromLocal(login: String) = async(Dispatchers.IO) {
    File(localDir, login).takeIf { it.exists() }
        ?.readText()
        ?.let {
            gson.fromJson(it, User::class.java)
        }
}

fun cacheUser(login: String, user: User) {
    File(localDir, login).writeText(gson.toJson(user))
}

data class Response<T>(val value: T, val isLocal: Boolean)

suspend fun selectOnResponse() {
    val login = "woxin123"
    GlobalScope.launch {
        val localDeferred = getUserFromLocal(login)
        val remoteDeferred = getUserFromApi(login)

        val userResponse = select<Response<User?>> {
            localDeferred.onAwait { Response(it, true) }
            remoteDeferred.onAwait { Response(it, false) }
        }
        userResponse.value?.let { println(it) }
        userResponse.isLocal.takeIf { it }?.let {
            val userFromApi = remoteDeferred.await()
            cacheUser(login, userFromApi)
            println(userFromApi)
        }
    }.join()
}

suspend fun flowOnUser() {
    coroutineScope {
        val login = "woxin123"
        listOf(::getUserFromApi, ::getUserFromLocal)
            .map { funaction ->
                funaction(login)
            }.map { deferred ->
                flow { emit(deferred.await()) }
            }.merge()
            .onEach { user ->
                println("Result: $user")
            }.launchIn(this)
    }
}

suspend fun main() {
//    selectOnResponse()
    flowOnUser()
}