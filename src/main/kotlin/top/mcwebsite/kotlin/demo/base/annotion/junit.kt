package top.mcwebsite.kotlin.demo.base.annotion

import org.junit.Test

const val TEST_TIMEOUT = 100L

@Test(timeout = TEST_TIMEOUT)
fun testMethod() {
    // ...
}