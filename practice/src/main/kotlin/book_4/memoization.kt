package book_4

import java.math.BigInteger
import java.util.concurrent.ConcurrentHashMap

fun fibo1(limit: Int): String =
    when {
        limit < 1 -> throw IllegalArgumentException()
        limit == 1 -> "1"
        else -> {
            var fibo1 = BigInteger.ONE
            var fibo2 = BigInteger.ONE
            var fibonacci: BigInteger
            val builder = StringBuilder("1, 1")
            for (i in 2 until limit) {
                fibonacci = fibo1.add(fibo2)
                builder.append(", ").append(fibonacci)
                fibo1 = fibo2
                fibo2 = fibonacci
            }
            builder.toString()
        }
    }

fun fibo(number: Int): String {
    tailrec fun fibo(acc: List<BigInteger>, acc1: BigInteger, acc2: BigInteger, x: BigInteger): List<BigInteger> =
        when (x) {
            BigInteger.ZERO -> acc
            BigInteger.ONE -> acc + (acc1 + acc2)
            else -> {
                fibo(
                    acc = acc + (acc1 + acc2),
                    acc1 = acc2,
                    acc2 = acc1 + acc2,
                    x = x - BigInteger.ONE,
                )
            }
        }

    val list = fibo(
        acc = listOf(),
        acc1 = BigInteger.ONE,
        acc2 = BigInteger.ZERO,
        x = BigInteger.valueOf(number.toLong()),
    )
    return makeString(list, ", ")
}

class Memoizer<T, U> private constructor() {
    private val cache = ConcurrentHashMap<T, U>()

    private fun doMemorize(function: (T) -> U): (T) -> U =
        { input ->
            cache.computeIfAbsent(input) {
                println("compute")
                function(it)
            }
        }

    companion object {
        fun <T, U> memoize(function: (T) -> U): (T) -> U =
            Memoizer<T, U>().doMemorize(function)
    }
}

fun longComputation(number: Int): Int {
    Thread.sleep(1000)
    return number
}

val f3m = Memoizer.memoize { x: Int ->
    Memoizer.memoize { y: Int ->
        Memoizer.memoize { z: Int ->
            longComputation(z) - (longComputation(y) + longComputation(x))
        }
    }
}

data class Tuple4<T, U, V, W>(
    val first: T,
    val second: U,
    val third: V,
    val fourth: W,
)

val ft = { (a, b, c, d): Tuple4<Int, Int, Int, Int> ->
    longComputation(a) + longComputation(b) - longComputation(c) * longComputation(d)
}

val ftm = Memoizer.memoize(ft)

fun main4_2() {
    val startTime1 = System.currentTimeMillis()
    val result1 = ftm(Tuple4(40, 41, 42, 43))
    val time1 = System.currentTimeMillis() - startTime1
    val startTime2 = System.currentTimeMillis()
    val result2 = ftm(Tuple4(40, 41, 42, 43))
    val time2 = System.currentTimeMillis() - startTime2
    println("First call to memoized function: result = $result1, time = $time1")
    println("Second call to memoized function: result = $result2, time = $time2")

//    val startTime1 = System.currentTimeMillis()
//    val result1 = f3m(41)(42)(43)
//    val time1 = System.currentTimeMillis() - startTime1
//    val startTime2 = System.currentTimeMillis()
//    val result2 = f3m(41)(42)(43)
//    val time2 = System.currentTimeMillis() - startTime2
//    println("First call to memoized function: result = $result1, time = $time1")
//    println("Second call to memoized function: result = $result2, time = $time2")

//    val startTime1 = System.currentTimeMillis()
//    val result1 = longComputation(43)
//    val time1 = System.currentTimeMillis() - startTime1
//    val memoizedLongComputation = Memoizer.memoize(::longComputation)
//    val startTime2 = System.currentTimeMillis()
//    val result2 = memoizedLongComputation(43)
//    val time2 = System.currentTimeMillis() - startTime2
//    val startTime3 = System.currentTimeMillis()
//    val result3 = memoizedLongComputation(43)
//    val time3 = System.currentTimeMillis() - startTime3
//    println("Call to nonmemoized function: result = $result1, time = $time1")
//    println("First call to memoized function: result = $result2, time = $time2")
//    println("Second call to memoized function: result = $result3, time = $time3")
}