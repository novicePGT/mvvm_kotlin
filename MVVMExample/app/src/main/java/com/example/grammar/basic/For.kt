package com.example.grammar.basic

/**
 * Kotiln 반복문
 * 1. for 문은 자바의 for-each 문과 같다.
 * 2. forEach를 사용해서 람다형식의 반복문을 사용할 수 있다.
 */
fun main() {
    val items = listOf(1, 2, 3, 4, 5)

    // 예시 1
    for (item in items) {
        println(item)
    }

    // 예시 2
    items.forEach {item ->
        println(item)
    }
}