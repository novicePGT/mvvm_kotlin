package com.example.grammar.basic

/**
 * String 특징
 * 1. String interpolation -> 문자열 보간법 : 이 언어는 ${name} 입니다.
 * 2. raw String -> """ : 이 언어는 Kotlin 입니다.
 */
fun main() {
    var name: String = "Kotlin"
    println(name.uppercase())
    println(name.lowercase())
    println("이 언어는 ${name} 입니다.")
    println("이 언어는 "+ name + "입니다.")
}