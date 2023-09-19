package com.example.grammar.basic

/**
 * List
 * 1. List는 수정 불가능한 리스트
 * 2. MutableList는 수정 가능한 리스트: mutableListOf<>() 로 제네릭이 붙어있는데 타입추론 때문에 생략 가능하다.
 */
fun main() {
    val items = mutableListOf<Int>(1,2,3,4,5)

    items.add(6)
    items.remove(3)

    print(items)
}