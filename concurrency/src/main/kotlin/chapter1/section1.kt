package chapter1

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    println("${Thread.activeCount()} threads active at the start")
    val time = measureTimeMillis {
        createCoroutines(amount = 3)
    }
    println("${Thread.activeCount()} threads active at the end")
    println("Took $time ms")
}

suspend fun createCoroutines(amount: Int) {
    val jobs = ArrayList<Job>()
    for (i in 1..amount) {
        jobs += GlobalScope.launch {
            println("Started $i in ${Thread.currentThread().name}")
            delay(1000)
            println("FinishedR $i in ${Thread.currentThread().name}")
        }
    }
    jobs.forEach {
        it.join()
    }
}