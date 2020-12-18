package book_2

fun main2() {
    val ls = mutableListOf("A String")
    val la: MutableList<Any> = mutableListOf()

    addAll(toList = la, fromList = ls)
    println(la)

    val bag3 = useBag(bag = BagImpl())
}

fun <T> addAll(
    toList: MutableList<T>,
    fromList: MutableList<out T>,
) {
    for (elem in fromList) toList.add(elem)
}

interface Bag<T> {
    fun get(): T
    fun use(t: T): Boolean
}

open class MyClassParent

class MyClass: MyClassParent()

class BagImpl: Bag<MyClassParent> {
    override fun get(): MyClassParent = MyClassParent()
    override fun use(t: MyClassParent): Boolean = true
}

fun useBag(bag: Bag<in MyClass>): Boolean {
    // bag 으로 작업 수행
    return true
}

class BagImpl2: Bag<MyClass> {
    override fun get(): MyClass = MyClass()
    override fun use(t: MyClass): Boolean = true
}

fun createBag(): Bag<out MyClass> = BagImpl2()
