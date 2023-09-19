package com.example.grammar.basic

/**
 * Generic
 * 제너릭은 타입을 체크하는 기능
 * 타입을 체크하는 기능을 컴파일 시간에 해주므로 안정성을 높이고 형 변환의 번거로움이 줄어든다.
 * 제너릭은 타입을 체크하는 기능이기 때문에 실행 시간에는 제거된다.
 * 또한, 같은 타입끼리 묶을 수 있어서 코드의 중복을 줄일 수 있다.
 */
fun main() {
    val box = Box<Int>(10)
    val box2 = Box<String>("Kotlin")

    println(box.value)
    println(box2.value)
}

class Box<T>(val value: T) {

}