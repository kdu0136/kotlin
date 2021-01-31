package chapter1

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val time = measureTimeMillis {
        val name = async { getName() }
        val lastName = async { getLastName() }
        println("Hello, ${name.await()} ${lastName.await()}")
    }
    println("Execution took $time ms")
}

// 명시적인 선언 - 연산이 동시에 실행되어야 하는 시점을 명시적으로 만드는 것이 중요
suspend fun getName(): String {
    delay(1000)
    return "Dongun"
}

suspend fun getLastName(): String {
    delay(1000)
    return "Kim"
}

fun primitives() {
    // 스레드 생성
    newSingleThreadContext(name = "")
    // 스레드 풀 생성
    newFixedThreadPoolContext(nThreads = 1, name = "")
}