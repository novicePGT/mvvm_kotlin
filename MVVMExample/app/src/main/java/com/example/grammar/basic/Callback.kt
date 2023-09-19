package com.example.grammar.basic

fun main() {
    myFunction {
        println("함수 호출")
    }


}

/**
 * Void -> Unit
 * callback 함수는 자주 쓰이게 될 것이다.
 */
fun myFunction(callback: () -> Unit) {
    println("함수 시작 !!!")
    callback()
    println("함수 끝 !!!")
}

/**
 * 함수 앞에 suspend 를 붙이면 정지함수라는 뜻으로 -> 코루틴에서 사용할 수 있는 함수가 된다.
 * 아래 함수가 시작이되면 끝날 때까지 대기를 하게 된다.
 * but, 코루틴은 비동기 처리를 위해 사용하는 기술이다.
 * suspend 가 붙은 함수는 suspend 안에서 사용이 가능한데, main 을 suspend 로 만드는 행동은 좋지 못하다.
 * suspend 주로 lifeCycleScofe 에서 사용하는데, 기본 kt 파일에서는 학습이 불가능하다.
 */
suspend fun mySuspendFunction(callback: () -> Unit) {
    println("함수 시작 !!!")
    callback()
    println("함수 끝 !!!")
}