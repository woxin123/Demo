@file:JvmName("IOUtils")
@file:JvmMultifileClass

package top.mcwebsite.kotlin.demo.base.annotion.jvmmultifileclass

import java.io.IOException
import java.io.Reader

fun closeReaderQuietly(input: Reader?) {
    try {
        input?.close()
    } catch (ioe: IOException) {
        // ignore
    }
}

