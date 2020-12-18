package book_3

import kotlin.math.PI
import kotlin.math.sin

data class Person(val name: String)

class FunFunctions {
    var percent1 = 5
    private var percent2 = 9
    val percent3 = 13

    fun add(a: Int, b: Int): Int = a + b // pure
    fun mult(a: Int, b: Int?): Int = 5 // impure
    fun div(a: Int, b: Int): Int = a / b // impure 제수가 0이면 예외 발생
    fun div(a: Double, b: Double): Double = a / b // pure -> 0.0으로 나누면 Nan(Not a Number)생성 -> NaN은 Double의 인스턴스
    fun applyTax1(a: Int) = a / 100 * (100 + percent1) // impure
    fun applyTax2(a: Int) = a / 100 * (100 + percent2) // pure -> 어떠한 코드도 percnet2 값을 변경하지 않기 때문

    // -> but 해당 값을 변경하는 다른 함수를 추가하면 pure 함수가 아님
    fun applyTax3(a: Int) = a / 100 * (100 + percent3) // pure
    fun append(i: Int, list: MutableList<Int>): List<Int> {
        list.add(i)
        return list
    } // impure

    fun append2(i: Int, list: List<Int>) = list + i // pure
}

val rDouble: (Int) -> Int = ::double

fun double(x: Int): Int = x * 2

fun square(n: Int): Int = n * n
fun triple(n: Int): Int = n * 3

fun <INNER, OUT, IN> compose(f: (INNER) -> OUT, g: (IN) -> INNER): (IN) -> OUT = { f(g(it)) }

fun cos(arg: Double) = compose(
    { x: Double -> PI / 2 - x },
    { y: Double -> sin(y) })(arg)

fun cos2(arg: Double): Double {
    fun f(x: Double): Double = PI / 2 - x
    fun sin(x: Double): Double = sin(x)

    return compose(::f, ::sin)(arg)
}

typealias IntBinOp = (Int) -> (Int) -> Int

val add: IntBinOp = { a -> { b -> a + b } }

val square: (Int) -> Int = { it * it }
val triple: (Int) -> Int = { it * 3 }

typealias IntUnaryOp = (Int) -> Int

val compose: (IntUnaryOp) -> (IntUnaryOp) -> IntUnaryOp = { x -> { y -> { z -> x(y(z)) } } }

fun <INNER, OUT, IN> higherCompose(): ((INNER) -> OUT) -> ((IN) -> INNER) -> (IN) -> OUT =
    { f: (INNER) -> OUT ->
        { g: (IN) -> INNER ->
            { x: IN ->
                f(g(x))
            }
        }
    }

fun <A, B, C> practice3_7(n: A, m: (A) -> (B) -> C): (B) -> C = m(n)

fun <A, B, C> practice3_8(n: B, m: (A) -> (B) -> C): (A) -> C =
    { a ->
        m(a)(n)
    }

fun <A, B, C, D> func(a: A, b: B, c: C, d: D): String = "$a, $b, $c, $d"

fun <A, B, C, D> funcCurry(): (A) -> (B) -> (C) -> (D) -> String =
    { a: A ->
        { b: B ->
            { c: C ->
                { d: D ->
                    "$a, $b, $c, $d"
                }
            }
        }
    }

fun <A, B, C> practice(f: (A, B) -> C): (A) -> (B) -> C =
    { a: A ->
        { b: B ->
            f(a, b)
        }
    }

val addTax: (price: Double) -> (tax: Double) -> Double =
    { price ->
        { tax ->
            price + price / 100 * tax
        }
    }

fun <T, U, V> swapArgs(f: (T) -> (U) -> V): (U) -> (T) -> V =
    { u: U ->
        { t: T ->
            f(t)(u)
        }
    }

data class Price private constructor(private val value: Double) {
    override fun toString(): String = value.toString()
    operator fun plus(price: Price) = Price(this.value + price.value)
    operator fun times(num: Int) = Price(this.value * num)

    companion object {
        val identity = Price(0.0)
        operator fun invoke(value: Double) =
            if (value > 0) Price(value)
            else throw IllegalArgumentException("Price must be positive or null")
    }
}

data class Weight private constructor(private val value: Double) {
    override fun toString(): String = value.toString()
    operator fun plus(weight: Weight) = Weight(this.value + weight.value)
    operator fun times(num: Int) = Weight(this.value * num)

    companion object {
        val identity = Weight(0.0)
        operator fun invoke(value: Double) =
            if (value > 0) Weight(value)
            else throw IllegalArgumentException("Weight must be positive or null")
    }
}

data class Product(val name: String, val price: Price, val weight: Weight)

data class OrderLine(val product: Product, val count: Int) {
    fun weight(): Weight = product.weight * count
    fun amount(): Price = product.price * count
}

fun main3() {
//    println(compose(::square, ::triple)(2))
//
//    println(add(1)(2))
//
//    println(compose(square)(triple)(2))
//    println(higherCompose<Int, Int, Int>()(square)(triple)(2))
//
//    val add9PercentTax: (Double) -> Double = swapArgs(addTax)(9.0)
//
//    val zeroPrice = Price(0.0)
//    val zeroWeight = Weight(0.0)
//    val priceAddition: (Double, Double) -> Double = { x, y -> x + y }

    val toothPaste = Product("Tooth paste", Price(1.5), Weight(0.5))
    val toothBrush = Product("Tooth brush", Price(3.5), Weight(0.3))
    val orderLines = listOf(
        OrderLine(toothPaste, 2),
        OrderLine(toothBrush, 3),
    )
    val weight: Weight =
        orderLines.fold(Weight.identity, { a, b -> a + b.weight() })
    val price: Price =
        orderLines.fold(Price.identity, { a, b -> a + b.amount() })
    println("Total price: $price")
    println("Total weight: $weight")
}

