package book_4

import java.math.BigInteger

fun append(s: String, c: Char): String = "$s$c"
fun prepend(c: Char, s: String): String = "$c$s"

fun sum(n: Int): Int {
    tailrec fun sum(s: Int, i: Int): Int =
        if (i > n) s else sum(s + i, i + 1)
    return sum(0, n)
}

fun inc(n: Int): Int = n + 1
fun dec(n: Int): Int = n - 1

// 양의 정수에 대해 작동하는 공제귀 함수
fun practice4_1(a: Int, b: Int): Int {
    if (a < 0 || b < 0) throw IllegalArgumentException("values are must over than zero")
    tailrec fun practice4_1(x: Int, y: Int): Int = if (y == 0) x else practice4_1(x = inc(n = x), y = dec(n = y))
    return practice4_1(x = a, y = b)
}

// 재귀적 계승 함수 값을 작성 (factorial)
object Factorial {
    val factorial: (BigInteger) -> BigInteger by lazy {
        { n: BigInteger ->
            if (n == BigInteger.ZERO) BigInteger.valueOf(1) else n * factorial(n - BigInteger.ONE)
        }
    }
}

fun <T> List<T>.head(): T =
    if (this.isEmpty())
        throw IllegalArgumentException("head called on empty list")
    else this[0]

fun <T> List<T>.tail(): List<T> =
    if (this.isEmpty())
        throw IllegalArgumentException("tail called on empty list")
    else this.drop(1)

fun fibonacci(index: Int): BigInteger {
    if (index < 0) throw IllegalArgumentException("fibonacci num is must plus number")
    tailrec fun fibonacci(val1: BigInteger, val2: BigInteger, i: BigInteger): BigInteger =
        when {
            (i == BigInteger.ZERO) -> BigInteger.ONE
            (i == BigInteger.ONE) -> val1 + val2
            else -> fibonacci(val1 = val2, val2 = val1 + val2, i = i - BigInteger.ONE)
        }
    return fibonacci(val1 = BigInteger.ZERO, val2 = BigInteger.ONE, i = BigInteger.valueOf(index.toLong()))
}

fun <T, U> foldLeft(list: List<T>, z: U, f: (U, T) -> U): U {
    tailrec fun foldLeft(list: List<T>, acc: U): U =
        if (list.isEmpty()) acc
        else foldLeft(list = list.tail(), acc = f(acc, list.head()))
    return foldLeft(list = list, acc = z)
}

fun <T, U> foldRight(list: List<T>, z: U, f: (T, U) -> U): U =
    if (list.isEmpty()) z
    else f(list.head(), foldRight(list = list.tail(), z = z, f = f))

fun sum(list: List<Int>): Int = foldLeft(list, 0, Int::plus)
fun string(list: List<Char>): String = foldLeft(list, "", String::plus)
fun <T> makeString(list: List<T>, delim: String): String =
    foldLeft(list, "") { s, t -> if (s.isEmpty()) "$t" else "$s$delim$t" }

fun toString(list: List<Char>): String =
    foldRight(list = list, z = "") { c, s -> prepend(c = c, s = s) }

fun <T> reverse(list: List<T>): List<T> =
    foldLeft(list = list, z = listOf(), f = ::prepend)
fun <T> prepend(list: List<T>, elem: T): List<T> = listOf(elem) + list

fun range(start: Int, end: Int): List<Int> {
    val result: MutableList<Int> = mutableListOf()
    (start until end).forEach(result::add)
    return result
}

fun <T>unfold(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> {
    val result: MutableList<T> = mutableListOf()
    var elem = seed
    while (p(elem)) {
        result.add(elem)
        elem = f(elem)
    }
    return result
}

fun main4_1() {
    val list = range(start = 1, end = 10)
    println(list)

//    val list = listOf(1, 2, 3, 4, 5)
//    println(reverse(list = list))

//    val fib = fibonacci(index = 1000)
//    println(fib)

//    val s = sum(list = listOf(1, 2, 3, 4, 5))
//    println(s)
//    val str = string(list = listOf('h', 'e', 'l', 'l', 'o'))
//    println(str)
//    val makeString = makeString(list = listOf("h", "e", "l", "l", "o"), delim = "_")
//    println(makeString)
//
//    val toString = toString(list = listOf('h', 'e', 'l', 'l', 'o'))
//    println(toString)
//    val add = practice4_1(a = 10, b = 50)
//    println(add)

//    println(Factorial.factorial(BigInteger("20")))
//    val str = toString(listOf('h', 'e', 'l', 'l', 'o'))
//    println(str)
//
//    println(sum(n = 10))
}