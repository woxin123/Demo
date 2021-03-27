@file:JvmName("IOUtils")
@file:JvmMultifileClass

package top.mcwebsite.kotlin.demo.base.annotion.jvmmultifileclass

import java.io.IOException
import java.io.InputStream

fun closeStreamQuietly(input: InputStream?) {
    try {
        input?.close()
    } catch (ioe: IOException) {
        // ignore
    }
}
