## 종류

| Function | Object reference | Return value | Is extension function |
| ---- | ---- | ---- | ---- |
| let | it | 람다 결과 | O |
| run | this | 람다 결과 | O |
| run | - | 람다 결과 | X - 객체 콘텍스트 없이 호출 |
| with | this | 람다 결과 | X - 인자값의 콘텍스트로 사용 |
| apply | this | 콘텍스트 객체 | O |
| also | it | 콘텍스트 객체 | O |

* 각 함수들의 간단하게 설명된 가이드
    * let
        * 람다식에서 non-null 객체로서 사용
        * 표현식을 로컬 범위의 변수로서 사용
        * `inline fun <T, R> T.let(block: (T) -> R): R`
    * apply
        * 객체 설정
        * `inline fun <T> T.apply(block: T.() -> Unit): T`
    * run
        * 객체 설정과 계산된 결과를 얻을 때
        * 식이 필요한 statement 실행 (non-extension)
        * `inline fun <R> run(block: () -> R): R`
        * `inline fun <T, R> T.run(block: T.() -> R): R`
    * also
        * 추가 효과 (콘텍스트 객체에 추가적인 작업을 할 때)
        * `inline fun <T> T.also(block: (T) -> Unit): T`
    * with
        * 호출된 객체에 grouping function 호출
        * `inline fun <T, R> with(receiver: T, block: T.() -> R): R`

## 사용 예제

```kotlin
// let
var someNullableVar: String? = "hello"
someNullableVar.let(::println)

// run
val service = MultiportService("https://some.url", 80)
val results = service.run {
    // run 대신 let 으로 동작해도 같은 결과가 리턴된다.
    port = 8080
    query(someRequest())
}

// run without object context
val someFunctionResult = run {
    println("someFunction is execute!")
    someFunction()
}

// with
val numbers = listOf("one", "two", "three")
with(numbers) {
    println("'with' is called with argument $this")
    println("It contains $size elements")
}

// apply
val person = Person("Name").apply {
    age = 20
    email = "test@test.com"
}

// also
val numbers = mutableListOf("one", "two", "three")
numbers.also { println("The list elements before adding new one: $it") }
    .add("four")
```
