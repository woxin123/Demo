package top.mcwebsite.handler.internal

import java.util.concurrent.TimeUnit

interface Delayed : Comparable<Delayed> {
    fun getDelay(timeUnit: TimeUnit): Long
}