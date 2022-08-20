## 복잡한 객체를 생성하기 위한 DSL 을 정의하라

* Domain Specific Language(이하 DSL) 는 복잡한 개체, 계층 구조를 가지는 객체들을 정의할 떄 굉장히 유용하다
* DSL 을 만드는 것은 힘들지만, 생성한 후에는 보일러플레이트와 복잡성을 숨기면서 개발자의 의도를 명확하게 표현할 수 있다
* DSL 이 사용되는 곳
    * 자료 또는 설정을 표현할 때도 활용될 수 있다
    * 코틀린 테스트를 활용하여 테스트 케이스에 사용될 수 있다
    * Gradle 설정을 정의할 때도 Gradle DSL 이 사용될 수 있다
* DSL 내부에도 코틀린이 제공하는 모든 것을 활용할 수 있다
* 코틀린 DSL 은 type-safe 이므로 여러가지 유용한 힌트를 활용할 수 있다

```kotlin
sourceSets {
    main {
        java.srcDir("src/some/path")
    }
}
// 위와 같은 형태로 DSL 을 활용 할 수 있다
```

### 리시버를 사용하는 함수 타입

* 함수 타입은 함수로 사용할 수 있는 객체를 나타내는 타입이다

```kotlin
inline fun <T> Iterable<T>.filter(
  predicate: (T) -> Boolean
): List<T> {
    ...
    if (predicate(someThing)) { ... }
    ...
}
```

* 위와 같이 `predicate: (T) -> Boolean` 과 같은 것을 함수 타입이라고 한다
    * `predicate`: 함수 타입 변수 이름
    * `(T)`: T 에 해당하는 아규먼트를 가진다
    * `-> Boolean`: Boolean 타입을 리턴한다
* 함수 타입을 만드는 기본적인 방법
    * 람다 표현식
    * 익명 함수
    * 함수 레퍼런스
* 함수 타입은 '함수를 나타내는 객체' 를 표현하는 타입이다
* `val myPlus = fun Int.(other: Int) = this + other` 와 같이 확장 함수를 나타내는 특별한 타입을 **리시버를 가진 함수 타입** 이라고 한다
    * 일반적인 함수 타입과 비슷하지만, 파라미터 앞에 리시버 타입이 추가되어 있고, 점 기호로 구분되어 있다
    * 이는 `val myPlus: Int.(Int) -> Int = fun Int.(other: Int) = this + other` 로 표현이 가능하다
* 이와 같이 함수는 람다식, 구체적으로 리시버를 가진 람다 표현식을 사용해서 정의할 수 있다
* 이렇게 하면 내부에 this 키워드가 확장 리시버를 참조하게 된다
    * `val myPlus: Int.(Int) -> Int = { this + it }`
* 리시버를 가진 익명 확장 함수와 람다 표현식은 다음과 같은 방법으로 호출할 수 있다
    * 일반적인 객체처럼 invoke 메서드를 사용 (`myPlus.invoke(1, 2)`)
    * 확장 함수가 아닌 함수처럼 사용 (`myPlus(1, 2)`)
    * 일반적인 확장 함수처럼 사용 (`1.myPlus(2)`)
* 리시버를 가진 함수 타입의 가장 중요한 특징은 this 의 참조 대상을 변경할 수 있다는 점이다
    * this 는 apply 함수에서 리시버 객체의 메서드와 프로퍼티를 간단하게 참조할 수 있게 해준다
  
### 사용자 정의 DSL 만들기

* 리시버를 가진 함수 타입은 코틀린 DSL 을 구성하는 가장 기본적인 블록이다

```kotlin
fun createTable(): TableDsl = table {
    // 별도의 리시버가 없고 톱레벨에 위치하므로, table 함수도 톱레벨에 있어야 한다
    tr {
        // tr 도 함수 아규먼트 내부에서 사용하고 있으므로, table 정의 내부에서만 허용되어야 한다
        // 즉, table 함수의 아규먼트는 tr 함수를 가지는 리시버를 가져야 하며, 하위에도 모두 똑같이 함수가 존재한다면
        // 해당 함수를 리시버로 가져야 한다
        for (i in 1..2) { +"value $i" }
    }
}
// HTML 표를 표현하는 간단한 DSL

fun table(init: TableBuilder.() -> Unit): TableBuilder {
    ...
}

class TableBuilder {
    fun tr(init: TrBuilder.() -> Unit) { ... }
}

class TrBuilder {
  // `+"value $i"` 와 같은 문장은 다음과 같이 동작한다
  // 이는 단순하게 문자열에 적용된 단항 + 연산자일 뿐이다
  var text = ""

  operator fun String.unaryPlus() {
    text += this
  }
}
```

* 이러한 DSL 이 제대로 동작하게 하려면 각각의 단계에서 다음과 같이 빌더를 만들고 파라미터를 활용해서 값들을 적절하게 초기화하면 된다
    * 이는 빌더에 아규먼트로 지정한 모든 데이터가 포함된다
    * 이러한 데이터가 필요한 데이터이다
    * 따라서 빌더를 리턴하거나, 이러한 데이터를 보유한 다른 객체를 만들어서 저장해둘 수 있다
    ```kotlin
    fun table(init: TableBuilder.() -> Unit): TableBuilder {
        val tableBuilder = TableBuilder()
        init.invoke(tableBuilder)
        return tableBuidler
    }
  
    fun table(init: TableBuilder.() -> Unit) = TableBuilder().apply(init)
    // 위와 같이 apply 함수를 활용하여 구현이 가능하다
    ```

* 정리하면 다음과 같은 형태로 간단하게 만들 수 있다
```kotlin
class TableBuilder {
    var trs = listOf<TrBuilder>()
  
    fun tr(init: TrBuilder.() -> Unit) {
        trs = trs + trBuilder().apply(init)
    }
}

class TrBuilder {
    var text = ""
  
    operator fun String.unaryPlus() {
        text += this
    }
}
```

### 언제 사용해야 할까?

* DSL 은 정보를 정의하는 방법을 제공한다
* 사용자 입장에서는 이러한 정보가 어떻게 활용되는지 명확하지 않으며, DSL 에 익숙하지 않은 사람에게는 DSL 은 혼란을 줄 수 있다
* DSL 을 정의한다는 것은 개발자의 인지적 혼란과 성능이라는 비용이 모두 발생한다
* 따라서 단순한 기능까지 DSL 을 사용한다는 것은 오버 스펙이고 불필요한 작업이다
* 다음과 같은 상황에서 DSL 을 표현하는 것이 유용하다
    * 복잡한 자료 구조
    * 계층적인 구조
    * 거대한 양의 데이터
* DSL 없이 빌더 또는 단순하게 생성자만 활용하여 우너하는 모든 것을 표현할 수 있다
* DSL 은 많이 사용되는 구조의 반복을 제거할 수 있게 해준다
    * 많이 사용되는 반복되는 코드가 있고, 이를 간단하게 만들 수 있는 별도의 코틀린 기능이 없다면 DSL 사용을 고려해 보는 것이 좋다
  
## 정리

* DSL 은 언어 내부에서 사용할 수 있는 특별한 언어이다
* 복잡한 객체와 복잡한 설정 등의 계층 구조를 가지는 객체를 간단하게 표현할 수 있게 해준다
* 하지만 DSL 구현은 DSL 에 익숙하지 않은 개발자에게 혼란과 어려움을 줄 수 있다
* 따라서 DSL 은 복잡한 객체를 만들거나 복잡한 계층 구조를 가지는 객체를 만들 때만 활용하는 것이 좋다
