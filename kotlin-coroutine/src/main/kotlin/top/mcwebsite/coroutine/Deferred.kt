package top.mcwebsite.coroutine

interface Deferred<T>: Job  {

    suspend fun await(): T

}