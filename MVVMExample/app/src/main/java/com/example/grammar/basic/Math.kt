package com.example.grammar.basic

import kotlin.random.Random

/**
 * 코틀린에서 math 클래스 활용하는 예시
 * kotlin.math.max(i, j), 이 친구는 코틀린으로 작성된 것
 * Random 함수 활용 기본
 */
fun main() {
    var i = 10
    var j = 20
    println(kotlin.math.max(i, j))

    val randomNumber = Random.nextInt(0, 100) // 0~99
    print(randomNumber)
}