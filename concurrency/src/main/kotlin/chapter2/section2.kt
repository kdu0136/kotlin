package chapter2

import kotlinx.coroutines.*

@InternalCoroutinesApi
fun main() = runBlocking {
    val dispatcher = newSingleThreadContext(name = "ServiceCall")
    val task = GlobalScope.launch(dispatcher) {
        printCurrentThread()
    }
//    task.join()
//    if (task.isCancelled) {
//        val exception = task.getCancellationException()
//        println("Error with message: ${exception.cause}")
//    } else {
//        println("Success")
//    }
    task.join()
    println("Complete")
}

