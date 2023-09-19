package com.example.grammar.basic

fun main() {
    val sum = sum(1, 3)
    println(sum)
    // 어느 값에 어떤 값인지 지정할 수 있다
    val sum2 = sum(a = 3, b = 2, c = 6)
    println(sum2)
}

/**
 * method overrode
 * c 에 default value 를 넣어서 c가 있던지 없던지 상관없이 사용할 수 있게 한다.
 * 2개의 메서드 오버로드를 쉽게 할 수 있는 장점이 있음.
 */
fun sum(a: Int, b: Int, c: Int = 0) :Int {
    return a + b + c
}