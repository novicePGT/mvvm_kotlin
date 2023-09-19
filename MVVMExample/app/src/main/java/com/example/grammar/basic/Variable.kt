package com.example.grammar.basic

const val MAX: Int = 5000

fun main() {
    /**
     * 코틀린 variable 특징
     * 1. var: mutable variable -> 변하기 쉬운 값
     * 2. val: immutable variable -> 변하기 어려운 값
     * 3. type inference -> 타입 추론
     * 4. type annotation -> 타입 명시
     * 5. compile time constant -> 컴파일 타임 상수 : const val, 메인보다 우선해서 컴파일이 되고 성능상 우위를 가져올 수 있음.
     * 타입을 지정하지 않아도 되지만, 래퍼클래스처럼 타입을 지정할 수 있다.
     */
    var i: Int = 10
    var name: String = "Kotlin"
    var point: Double = 3.3

    println(i)
    println(name)
    print(point)
}