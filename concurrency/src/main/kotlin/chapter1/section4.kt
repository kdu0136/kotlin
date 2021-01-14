package chapter1

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

// 동시성 프로그래밍이 어려운 이유
fun main() = runBlocking{
    // asyncGetUserInfo 가 1초 이내로 끝날꺼라고 가정해서 생기는 문제
    asyncGetUserInfo(id = 1)
    // Do some other operation
    delay(1000)

    println("User ${user.id} is ${user.name}")
}

// Race condition (동시성 코드가 항상 특정한 순서로 실행될 것이라 가정하는 오류)
data class UserInfo(val name: String, val lastName: String, val id: Int)

lateinit var user: UserInfo

fun asyncGetUserInfo(id: Int) = GlobalScope.async {
    delay(1001)
    user = UserInfo(
        id = id,
        name = "Susan",
        lastName = "Calvin",
    )
}