## 결과 부족이 발생한 경우 null 과 Failure 를 사용하라

* 인터넷(통신) 문제, 조건에 맞는 요소가 없거나 파싱 대상이 원하는 바의 데이터 타입과 다른 경우와 같이 여러가지 원인으로 함수가 원하는 결과를 만들어 낼 수 없을 수 있다
* 이러한 상황을 처리하는 매커니즘은 크게 다음과 같다
    1. null 또는 `sealed` 클래스(일반적으로 Failure 라는 이름을 붙인다) 을 리턴
    2. 예외 throw

### null 과 Failure

* 예상되는 오류를 표현할 때 명시적ㅇ티고 효율적이며, 간단한 방법으로 처리할 수 있어서 좋다
* 따라서 예측할 수 있는 범위의 오류는 null 과 Failure 를 사용하고, 예측하기 어려운 예외적인 범위의 오류는 예외를 throw 하여 처리하는 것이 좋다

```kotlin
inline fun <reified T> String.readObjectOrNull(): T? {
    ...
    if (incorrectSign) return null
    ...
    return result
}

inline fun <reified T> String.readObject(): Result<T> {
    ...
    if (incoreectSign) return Failure(JsonParsingException())
    ...
    return result
}

sealed class Result<out T>
class Success<out T>(val result: T): Result<T>()
class Failure(val throwable: Throwable): Result<Nothing>()
// Result 와 같은 union type 을 리턴한다면, when 표현식을 사용하여 처리할 수 있다
/**
 * val age = when(person) {
 *      is Success -> person.age
 *      is Failure -> -1
 * }
 */

class JsonParsingException: Exception()
// 이렇게 표시되는 오류는 다루기 쉬우며 놓치기 어렵다
// null 을 처리해야 한다면 safe call 또는 Elvis 연산자와 같은 null-safety 기능을 활용하면 된다

val age = userText.readObjectOrNull<Person>()?.age ?: -1
```

* 위와 같은 형태의 오류 처리 방식은 try-catch 블록보다 효율적이고, 사용하기 쉽고 더 명확하다
* 예외는 놓칠 수 있으며, 어플리케이션을 중지시킬 수 있지만, null/sealed result 클래스는 명시적으로 처리해야 하며 어플리케이션의 흐름을 중지하지 않는다

#### null vs sealed class 그리고 Failure

* 추가적인 정보를 전달해야 한다면 sealed class 를, 아니면 null 을 사용하면 된다
* Failure 는 처리할 때 필요한 정보를 가질 수 있다

### exception throw

* 예외는 정보를 전달하는 방법이 아닌, 예외는 잘못된 특별한 상황을 나타내어야 하고 처리되어야 한다
* 예외는 예외적인 상황이 발생했을 때 사용하는 것이 좋다
* 많은 개발자는 예외가 전파되는 과정을 제대로 추적하지 못한다
* 코틀린의 모든 예외는 unchecked 예외이며, 사용자가 이러한 예외를 처리하지 않을 수도 있으며 관련된 내용이 문서에 드러나지 않는다
* API 를 사용할때 예외와 관련된 사항을 단순하게 메서드를 사용하면서 파악하기는 힘들다
* 예외는 예외적인 상황을 처리하기 위해 만들어졌으므로 명시적인 테스트만큼 빠르게 동작하지 않는다
* try-catch 블록 내부에 코드를 배치하면, 컴파일러가 할 수 있는 최적화가 제한된다

### 일반적인 함수의 사용 형태

* 리스트에서 예상할 수 있을때와 예상할 수 없을때 다음과 같은 형태로 함수를 사용할 수 있다
    * `get`: 특정 위치에 있는 요소를 추출할 때 사용하며, 요청한 위치에 데이터가 없다면 `IndexOutOfBoundsException` 이 발생한다
    * `getOrNull`: out of range 오류가 발생할 수 있을 경우 사용되며, 발생하면 null 이 리턴된다
    * 이 외에 `getOrDefault` 와 같은 형태로 사용이 될 수 있으나, `getOrNull ?: something` 처름 Elvis 연산자를 이용하는 것이 쉽다

## 결론

* 개발자는 항상 자신이 요소를 안전하게 추출할 거라 생각하기에 nullable 을 리턴하면 안된다
* 개발자에게 null 이 발생할 수 있다는 것을 경고하려면, getOrNull 등과 같이 무엇이 리턴되는지 예측할 수 있게 하는 것이 좋다
