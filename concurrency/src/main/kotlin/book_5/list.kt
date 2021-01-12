package book_5

open class Pair<A, B>(val first: A, val second: B)

sealed class List<out A> { // sealed 클래스는 암묵적으로 abstract class, 생성자는 암묵적 private
    abstract fun isEmpty(): Boolean // 각 확장 클래스는 abstract isEmpty 함수를 다르게 구현
    abstract fun init(): List<A>

    private object Nil : List<Nothing>() { // 빈 리스트를 표현하는 Nil 하위 클래스, List 클래스 안에서 확장 클래스를 정의하고 비공개로 선언
        override fun isEmpty(): Boolean = true
        override fun toString(): String = "[NIL]"
        override fun init(): List<Nothing> = throw IllegalArgumentException("init called on an empty list")
    }

    private class Cons<A>(internal val head: A, internal val tail: List<A>) :
        List<A>() { // 비어 있지 않은 리스트를 표현하는 Cons 확장 클래스
        override fun isEmpty(): Boolean = false
        override fun toString(): String = "[${toString("", this)}NIL]"
        private tailrec fun toString(acc: String, list: List<A>): String =
            when (list) { // 공재귀 함수로 toString 구현
                is Nil -> acc
                is Cons -> toString("$acc${list.head}, ", list.tail)
            }

        override fun init(): List<A> = reverse().drop(1).reverse()
    }

    fun cons(a: @UnsafeVariance A): List<A> = Cons(a, this)

    fun setHead(a: @UnsafeVariance A): List<A> = when (this) {
        is Nil -> throw IllegalArgumentException("setHead called on an empty list")
        is Cons -> tail.cons(a)
    }

    fun drop(n: Int): List<A> {
        tailrec fun drop(n: Int, list: List<A>): List<A> =
            if (n <= 0) list else when (list) {
                is Nil -> list
                is Cons -> drop(n - 1, list.tail)
            }
        return drop(n, this)
    }

    fun dropWhile(p: (A) -> Boolean): List<A> {
        tailrec fun dropWhile(list: List<A>, p: (A) -> Boolean): List<A> = when (list) {
            is Nil -> list
            is Cons -> if (p(list.head)) dropWhile(list.tail, p) else list
        }

        return dropWhile(this, p)
    }

    fun concat(list: List<@UnsafeVariance A>): List<A> = concat(this, list)

    fun reverse(): List<A> = reverse(invoke(), this)

    companion object {
        operator
        fun <A> invoke(vararg az: A): List<A> = // operator 키워드를 사용해 선언한 invoke 함수는 클래스이름()처럼 호출 가능
            az.foldRight(Nil as List<A>) { a: A, list: List<A> ->
                Cons(a, list) // foldRight 함수의 첫 번째 인자로 쓰이는 Nil을 List<A>로 명시적으로 타입 변환
            }

        tailrec fun <A> drop(list: List<A>, n: Int): List<A> = when (list) {
            is Nil -> list
            is Cons -> if (n <= 0) list else drop(list.tail, n - 1)
        }

        private fun <A> concat(list1: List<A>, list2: List<A>): List<A> = when (list1) {
            is Nil -> list2
            is Cons -> concat(list1.tail, list2).cons(list1.head)
        }

        private fun <A> reverse(acc: List<A>, list: List<A>): List<A> = when (list) {
            is Nil -> acc
            is Cons -> reverse(acc.cons(list.head), list.tail)
        }

        fun <A, B> operation(
            list: List<A>,
            identity: B,
            operator: (A) -> (B) -> (B),
        ): B = when (list) {
            is Nil -> identity
            is Cons -> operator(list.head)(operation(list.tail, identity, operator))
        }
    }
}

fun main5() {
    val list: List<Int> = List(1, 2, 3)
    println(list)
    val list2 = list.cons(0)
    println(list2)
    val list3 = list.setHead(0)
    println(list3)
    val list4 = list.drop(2)
    println(list4)
    val list5 = List.drop(list, 2)
    println(list5)
    val list6 = list.drop(2).setHead(0)
    println(list6)
    val list7 = list.dropWhile { e -> e <= 10 }
    println(list7)
    val list8 = list.concat(list6)
    println(list8)
    val list9 = list.init()
    println(list9)
    val list10 = list6.init()
    println(list10)
    val listSum = List.operation(list, 0) { x -> { y -> x + y } }
    println(listSum)
    val listDouble: List<Double> = List(1.0, 2.0, 3.0, 4.0)
    val listProduct = List.operation(listDouble, 1.0) { x -> { y -> x * y } }
    println(listProduct)

}



