package com.example.grammar.basic

/**
 * 자바는 보통 public 이지만, Kotlin 은 반대로 보통 private 이다.
 * 따라서, 상속을 설계할 때는 open 키워드를 붙여주어야한다.
 */
fun main() {
    val dog: Animal = Animal.Dog()
    val cat = Animal.Cat()

    // 타입 체크할 때는 is 키워드를 사용한다.
    if (dog is Animal.Dog) {
        println("멍멍이")
    }

    // 강제로 타입을 캐스팅할 때는 as 키워드를 사용한다.
    val dog2 = dog as Animal.Cat
    if (dog2 is Animal.Cat) { // 이거는 당연히 터지는 예제
        println("야옹이")
    }
}

// 클래스 상속
open class User {}

class SuperUser : User() {}

interface Drawable {
    fun draw()
}

// 추상 클래스 상속
abstract class Animal {
    open fun walk() {
        print("이동")
}

class Dog : Animal(), Drawable {
    override fun walk() {
        println("껑충")
    }

    override fun draw() {
    }
}

class Cat : Animal(), Drawable {
    override fun walk() {
        println("살금")
    }

    override fun draw() {
    }
}
}