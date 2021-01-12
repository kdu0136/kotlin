
import chapter1.createCoroutines
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val time = measureTimeMillis {
        createCoroutines(amount = 1000000)
    }
    println("Took $time ms")
}