## 함수 타입 파라미터를 갖는 함수에 inline 한정자를 붙여라

* 코틀린 표준 라이브러리의 고차 함수 대부분은 inline 한정자가 붙어있다

```kotlin
inline fun repeat(times: Int, action: (Int) -> Unit) {
    for (index in 0 until times) {
        action(index)
    }
}
```

* inline 한정자의 역할은 컴파일 시점에 '함수를 호출하는 부분' 을 '함수의 본문' 으로 대체하는 것이다

```kotlin
repeat(10) { print(it) }
// 컴파일 시점에 아래와 같이 대체된다

for (index in 0 until 10) { print(index) }
```

* 일반적인 함수를 호출하면 함수 본문으로 점프하고 본문의 모든 문장을 호출한뒤에 함수를 호출했던 위치로 다시 점프하는 과정을 거친다
* inline 을 활용하면, 호출하는 부분이 함수의 본문으로 대체되므로 이러한 점프가 발생하지 않는다
* inline 한정자를 사용하면 다음과 같은 장점이 발생한다
    * 타입 아규먼트에 reified 한정자를 붙여서 사용할 수 있다
    * 함수 타입 파라미터를 가진 함수가 훨씬 빠르게 동작한다
    * 비지역(non-local) 리턴을 사용할 수 있다
    
### 타입 아규먼트를 reified 로 사용할 수 있다

* 구버전의 자바에는 제네릭이 없었으며, JVM 바이트 코드에는 제네릭이 존재하지 않으므로 컴파일시 제네릭 타입과 관련된 내용은 제거된다
    * 이는 객체가 `List<Int>` 인지 확인은 불가능하나 `List` 인지는 확인이 가능하다
    
```kotlin
fun <T> printTypeName() {
    print(T::class.simpleName)
    // 타입 파라미터에 대한 연산도 오류가 발생한다
}
// 함수를 인라인으로 만들면 이러한 제한을 무시할 수 있다

inline fun <reified T> printTypeName() {
    print(T::class.simpleName)
}

printTypeName<Int>() // Int
print(Int::class.simpleName) // Int
// 위와 같이 본문이 실제로 대체된다
```

### 함수 타입 파라미터를 가진 함수가 훨씬 빠르게 동작한다

* 모든 함수는 inline 한정자를 붙이면 조금 더 빠르게 동작한다
    * 함수 호출과 리턴을 위해 점프하는 과정과 백스택을 추적하는 과정이 없기 때문이다
* 표준 라이브러리에 있는 간단한 함수들에는 대부분 inline 한정자가 붙어 있다
    * 하지만 함수 파라미터를 가지지 않는 함수에서는 이러한 차이가 큰 성능 차이를 발생시키지 않는다
* 함수 리터럴을 사용하여 만들어진 종류의 객체는 어떤 방식으로든 저장되고 유지되어야 한다
    * 코틀린/JS 에서는 자바스크립트가 함수를 일급 객체로 처리하므로 굉장히 간단하게 변환이 이루어진다
    * 코틀린/JVM 에서는 JVM 익명 클래스 또는 일반 클래스를 기반으로 함수를 객체로 만들어 낸다
```kotlin
var lambda: () -> Unit = {}
// 아래와 같은 클래스로 컴파일 된다

// java
Function0<Unit> lambda = new Function0<Unit>() {
    public Unit invoke() {}
}

// 별도의 파일에 정의되어 있는 일반 클래스로 컴파일시
public class Test$lambda implements Function0<Unit> {
    public Unit invoke() {}
}

// 사용
Function0 lambda = new Test$lambda()
```

* JVM 에서 아규먼트가 없는 함수 타입은 Function0 타입으로 변환된다
* 그 외 타입의 함수는 다음과 같은 형태로 변환된다
    * () -> Unit: Function0<Unit>
    * () -> Int: Function0<Int>
    * (Int) -> Int: Function1<Int, Int>
    * (Int, Int) -> Int: Function2<Int, Int, Int>
* 이러한 모든 인터페이스는 모두 코틀린 컴파일러에 의해 생성된다
* 요청이 있을 때 생성되므로 이를 명시적으로 사용할 수 없으며 대신 함수 타입을 사용할 수 있다

```kotlin
inline fun repeat(times: Int, action: (Int) -> Unit) {
    for (index in 0 until times) {
        action(index)
    }
}

fun repeatNoinline(times: Int, action: (Int) -> Unit) {
    for (index in 0 until times) {
        action(index)
    }
}
// 위와 같은 코드는 함수 본문을 객체로 wrap 하면 코드의 속도가 느려지므로, inline 이 있는 함수가 더 빠르다
```

* '인라인 함수' 와 '인라인 함수가 아닌 함수' 의 더 중요한 차이는 함수 리터럴 내부에서 지역 변수를 캡처할 때 확인할 수 있다
    * 캡처된 값은 객체로 래핑해야 하며, 사용할 때마다 객체를 통해 작업이 이루어져야 한다
    
```kotlin
var l = 1L
noinlineRepeat(100_000_000) { l += it }
// 인라인이 아닌 람다 표현식에서는 지역 변수 l 을 직접 사용이 불가능하다

val a = Ref.LongRef()
a.element = 1L
noinlineRepeat(100_000_000) {
    a.element = a.element += it
}
// l 은 위와 같이 컴파일 과정 중에 래퍼런스 객체로 래핑되고 람다 표현식 내부에서는 이를 사용한다
// 이는 함수가 객체로 컴파일되고, 지역 변수가 래핑되어 발생하는 문제가 누적이 된다
```

* 일반적으로 함수 타입의 파라미터가 어떤 식으로 동작하는지 이해하기 어려우므로, 함수 타입 파라미터를 활용해서 유틸리티 함수를 만들때는 인라인을 붙여주는게 일반적으로 좋다

### 비지역적 리턴을 사용할 수 있다

```kotlin
fun repeatNoinline(times: Int, action: (Int) -> Unit) {
    for (index in 0 until times) {
        action(index)
    }
}

fun main() {
    repeatNoinline(10) {
        print(it)
        return // 내부에서 리턴을 사용할 수 없다
    }
}
```

* 위와 같은 현상은 함수 리터럴이 컴파일될 때 함수가 객체로 래핑되어서 발생하는 문제이다
* 함수가 다른 클래스에 위치하므로 return 을 사용하여 호출한 곳으로 돌아올 수 없다
* 이는 인라인 함수라면 함수의 본문으로 대체되기 때문에 문제가 없다

```kotlin
fun main() {
    repeat(10) {
        print(it)
        return // ok
    }
}
```

### inline 한정자의 비용

* 인라인 함수는 재귀적으로 사용하면 무한하게 대체되는 문제가 발생하므로 사용할 수 없다
    * 이는 Intellij 에서 오류로 잡아주지 못하므로 위험하다
    
```kotlin
inilne fun a() { b() }
inilne fun b() { c() }
inilne fun c() { a() }
```

* 인라인 함수는 더 많은 가시성 제한을 가진 요소를 사용할 수 없다
    * public 인라인 함수 내부에서 private/internal 가시성을 가진 함수와 프로퍼티를 사용할 수 없다
    ```kotlin
    internal inline fun read() {
        val reader = Reader() // 오류
    }
    
    private class Reader { ... }
    ```
* 인라인 함수는 구현을 숨길 수 없으므로 클래스에 거의 사용되지 않는다
* inline 한정자를 남용하면 코드의 크기가 쉽게 커진다
    * 서로 호출하는 인라인 함수가 많아지면 코드가 기하급수적으로 증가한다
  
### crossinline 과 noinline

* 함수를 인라인으로 만들고 싶으나, 일부 함수 타입 파라미터는 inline 으로 받고 싶지 않은 경우 다음과 같은 한정자를 사용할 수 있다
    * crossinline
        * 아규먼트로 인라인 함수를 받지만 비지역적 리턴을 하는 함수는 받을 수 없게 만든다
        * 인라인으로 만들지 않은 다른 람다 표현식과 조합하여 사용할 때 문제가 발생하는 경우 활용한다
    * noinline
        * 아규먼트로 인라인 함수를 받을 수 없게 만든다
        * 인라인 함수가 아닌 함수를 아규먼트로 사용하고 싶을 때 활용한다
  
```kotlin
inline fun requestNewToken(
    hasToken: Boolean,
    crossinilne onRefresh: () -> Unit,
    noinline onGenerate: () -> Unit
) {
    if (hasToken) {
        httpCall("get-0token", onGenerate)
    } else {
        httpCall("refresh-token") {
            onRefresh()
            // Non-local 리턴이 허용되지 않는 컨텍스트에서
            // inline 함수를 사용하고 싶다면 crossinline 을 사용한다
            onGenerate()
        }
    }
}

fun httpCall(url: String, callback: () -> Unit) { ... }
```

* `crossinline`/`noinline` 의 의미를 확실하게 기억하면 좋지만, intellij 가 필요할 때 알아서 제안해 준다

## 정리

* 인라인 함수가 사용되는 주요 사례
    * print 함수처럼 매우 많이 사용되는 경우
    * filterIsInstance 함수처럼 타입 아규먼트로 reified 타입을 전달받는 경우
    * 함수 타입 파라미터를 가지는 톱레벨 함수를 정의해야 하는 경우
        * 특히 컬렉션 처리 함수와 같은 헬퍼 함수, 스코프 함수, 톱레벨 유틸리티 함수 등
* API 를 정의할 때 인라인 함수를 사용하는 경우는 거의 없다
* 한 인라인 함수가 다른 인라인 함수를 호출할 경우 코드가 기하급수적으로 많아질 수 있음을 주의해야 한다
