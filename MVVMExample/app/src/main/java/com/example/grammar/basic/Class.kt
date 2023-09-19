package com.example.grammar.basic

fun main() {
    val john = Person("John", 34, "Seoul")
    print(john.name)
    print(john.age)
}

/**
 * kotlin 에서 매개변수 안에 생성자를 넣을 수 있다
 * 생성자에게 전달을하고 외부에는 노출시키고 싶지 않을 때는 private를 쓰면 된다.
 * -> Getter 조차 제공 불가능
 */
class Person(
    val name: String,
    val age: Int,
    private val address: String
)