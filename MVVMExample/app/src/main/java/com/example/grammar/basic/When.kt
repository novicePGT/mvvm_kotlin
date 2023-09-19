package com.example.grammar.basic

/**
 * when 문은 자바의 if 문과 같다
 * 1. when 문은 조건을 만족하는 블록을 실행한다 : 조건 여러 개를 묶을 수 있는 것
 *
 */
fun main() {
    var i = 5

    when {
        i > 10 -> {
            println("i는 10보다 크다.")
        }
        i > 5 -> {
            println("i는 5보다 크다.")
        }
        else -> {
            println("i는 5보다 작거나 같다.")
        }
    }

    // kotlin 에서 3항 연산자는 없다.
    val result = if (i > 10) true else false
}