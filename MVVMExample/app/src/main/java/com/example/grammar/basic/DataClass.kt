package com.example.grammar.basic

fun main() {
    val john = Person2("John", 34)
    val john2 = Person2("John", 34)

    println(john)
    println(john2)
    println(john == john2)
    println(john.hobby)
    john.some()
    println(john.hobby)
}

/**
 * data class
 * toString(), hashCode(), equals(), copy() 를 자동으로 생성해준다.
 * data class 는 주로 DTO(Data Transfer Object) 에서 많이 사용된다.
 * -> DTO : 데이터를 전송하기 위한 객체
 */
data class Person2(
    val name: String,
    val age: Int
) {
    var hobby = "축구"
        private set
        get() = "취미: $field"

    init {
        println("DataClases -> name : $name, age : $age")
    }

    fun some() {
        hobby = "농구"
    }
}