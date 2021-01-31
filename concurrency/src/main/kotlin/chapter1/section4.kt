package chapter1

import kotlinx.coroutines.*

// 동시성 프로그래밍이 어려운 이유
fun main() = runBlocking {
    // asyncGetUserInfo 가 1초 이내로 끝날꺼라고 가정해서 생기는 문제 (레이스 컨디션)
//    asyncGetUserInfo(id = 1)
//    // Do some other operation
//    delay(1000)
//
//    println("User ${user.id} is ${user.name}")

    // 원자성 위반 코드
//    val workerA = asyncIncrement(by = 2000)
//    val workerB = asyncIncrement(by = 100)
//    workerA.await()
//    workerB.await()
//    println("counter: $counter")

    circularDependencies()
}

// Race condition (동시성 코드가 항상 특정한 순서로 실행될 것이라 가정하는 오류)
data class UserInfo(val name: String, val lastName: String, val id: Int)

lateinit var user: UserInfo

// 레이스 컨디션 - 코드를 동시성으로 작성했지만 순차적 코드로 동작할 것이라고 예상하고 작성하는 문제
private fun asyncGetUserInfo(id: Int) = GlobalScope.async {
    delay(1001)
    user = UserInfo(
        id = id,
        name = "Susan",
        lastName = "Calvin",
    )
}

// 원자성 위반 - 데이터 수정이 동시성 작업에 의해 손실이 발생할 수 있는 문제
var counter = 0
private fun asyncIncrement(by: Int) = GlobalScope.async {
    for (i in 0 until by) {
        counter++
    }
}

// 교착 상태 - 다른 스레드에서 작업이 완료되는 동안 실행을 일시 중단하거나 차단할 필요가 있다. 이러한 상황에서
// 순환적 의존성으로 인해 전체 어플리케이션의 실행이 중단되는 상황이 발생하는 문제 (레이스 컨디션과 자주 같이 발생)
lateinit var jobA: Job
lateinit var jobB: Job
private fun circularDependencies() = runBlocking {
    jobA = GlobalScope.launch {
        delay(1000)
        // wait for jobB to finish
        jobB.join()
    }
    jobB = GlobalScope.launch {
        // wait for jobA to finish
        jobA.join()
    }

    // wait for jobA to finish
    jobA.join()
    println("Finish")
}

// 라이브 락 - 어플리케이션이 올바르게 실행을 계속할 수 없을 때 발생한는 교착 상태화 유사
// 라이브 락이 진행될 때 어플리케이션의 상태는 지속적으로 변하지만 어플리케이션이 정상 실행으로 돌아오지 못하게 하는 방향으로 상태가 변화
// (교착 상태를 복구하려는 시도가 라이브락를 만들어 낼 수 있음)
// ex) A와  B 마주보고있는 두 사람이 각각 한쪽 방향으로 이동해서 서로를 피하려고 할때
// A가 B를 피하려고 왼쪽으로 이동할 때, B는 A를 피하려고 오른쪽으로 이동