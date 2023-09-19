package com.example.grammar.basic

/**
 * null safety
 * 타입 뒤에 ? 를 붙이면 null 을 가질 수 있는 타입이 된다.
 * name?.let : name 이 null 이 아닐 때만 실행
 * -> if 문을 붙이는 null check 보다 간결하게 사용할 수 있다.
 */
fun main() {
    var name: String? = null
    name = null

    var name2: String = "--"

    // null check
    name?.let {
        name2 = name
    }

    println(name)
    println(name2)
}