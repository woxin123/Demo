package top.mcwebsite.kotlin.demo.coroutine

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.yield
import java.io.InputStream
import java.io.OutputStream
import kotlin.coroutines.coroutineContext


fun InputStream.copyTo(
    out: OutputStream,
    bufferSize: Int = DEFAULT_BUFFER_SIZE
): Long {
    var bytesCopied: Long = 0
    val buffer = ByteArray(bufferSize)
    var bytes = read(buffer)
    while (bytes >= 0) {
        out.write(buffer, 0, bytes)
        bytesCopied += bytes
        bytes = read(buffer)
    }
    return bytesCopied
}

//@InternalCoroutinesApi
//suspend fun InputStream.copyToSuspend(
//    out: OutputStream,
//    bufferSize: Int = DEFAULT_BUFFER_SIZE
//): Long {
//    var bytesCopied: Long = 0
//    val buffer = ByteArray(bufferSize)
//    var bytes = read(buffer)
//    val job = coroutineContext[Job]
//    while (bytes >= 0) {
//        job?.let {
//            it.takeIf { it.isActive } ?: throw job.getCancellationException()
//        }
//        out.write(buffer, 0, bytes)
//        bytesCopied += bytes
//        bytes = read(buffer)
//    }
//    return bytesCopied
//}

@InternalCoroutinesApi
suspend fun InputStream.copyToSuspend(
    out: OutputStream,
    bufferSize: Int = DEFAULT_BUFFER_SIZE
): Long {
    var bytesCopied: Long = 0
    val buffer = ByteArray(bufferSize)
    var bytes = read(buffer)
    while (bytes >= 0) {
        yield()
       out.write(buffer, 0, bytes)
        bytesCopied += bytes
        bytes = read(buffer)
    }
    return bytesCopied
}
