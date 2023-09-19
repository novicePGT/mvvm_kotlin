package com.example.grammar.basic

/**
 * Casting: 형변환
 */
fun main() {
    // 숫자 캐스팅
    var i = 10
    var l = 20L
    l = i.toLong()
    i = l.toInt()

    // 문자열 캐스팅
    var name = "10"
    i = name.toInt()
}