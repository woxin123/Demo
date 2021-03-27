package top.mcwebsite.kotlin.demo.base.annotion

import java.io.IOException
import java.io.Writer
import kotlin.jvm.Throws

@Throws(IOException::class)
fun closeQuietly(output: Writer?) {
    output?.close()
}