package com.example.grammar.basic

fun main() {
    val items = arrayListOf(1, 2, 3)
    items[0] = 5
    items.set(2, 6)

    try {
        val item = items[3]
    } catch (e: Exception) {
        println(e)
    }

    print(items)
}