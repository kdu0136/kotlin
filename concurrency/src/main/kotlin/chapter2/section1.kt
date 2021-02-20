package chapter2

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

@InternalCoroutinesApi
fun main() = runBlocking {
    val task = GlobalScope.async {
        doSomeThing()
    }
//    task.join()
//    if (task.isCancelled) {
//        val exception = task.getCancellationException()
//        println("Error with message: ${exception.cause}")
//    } else {
//        println("Success")
//    }
    task.await()
    println("Complete")
}

fun doSomeThing() {
    throw UnsupportedOperationException("Can't do")
}

fun printCurrentThread() = println("Running in thread [${Thread.currentThread().name}]")
